package us.my_family.engine.actor

import akka.actor.{Actor, ActorLogging, Props}
import us.my_family.engine.GameState.{Board, Position}
import us.my_family.engine._
import us.my_family.engine.actor.MoveValidator.{InvalidMove, ValidMove, ValidateMove}

object MoveValidator {


  def props() = Props(new MoveValidator())

  case class ValidateMove(board: Board, from: Position, to: Position)

  case class ValidMove(board: Board, from: Position, to: Position)

  case class InvalidMove(board: Board, from: Position, to: Position)

  object Pieces {

    object King {
      def unapply(move: ValidateMove): Boolean = move.board.pieceAt(move.from).exists(_.isInstanceOf[King])
    }

    object Queen {
      def unapply(move: ValidateMove): Boolean = move.board.pieceAt(move.from).exists(_.isInstanceOf[Queen])
    }

    object Rook {
      def unapply(move: ValidateMove): Boolean = move.board.pieceAt(move.from).exists(_.isInstanceOf[Rook])
    }

    object Knight {
      def unapply(move: ValidateMove): Boolean = move.board.pieceAt(move.from).exists(_.isInstanceOf[Knight])
    }

    object Bishop {
      def unapply(move: ValidateMove): Boolean = move.board.pieceAt(move.from).exists(_.isInstanceOf[Bishop])
    }

    object Pawn {
      def unapply(move: ValidateMove): Boolean = move.board.pieceAt(move.from).exists(_.isInstanceOf[Pawn])
    }

  }

  object Moves {

    object Up {
      /**
        * Extracts the board, piece, position from, and position to of the piece is being moved up
        */
      def unapply(move: ValidateMove): Option[(Board, Option[Piece], Position, Position)] =
        Some(move).filter(m => m.from.file == m.to.file && m.from.rank > m.to.rank).map(m => (m.board, m.board.pieceAt(m.from), m.from, m.to))
    }

    object Down {
      def unapply(move: ValidateMove): Option[(Board, Option[Piece], Position, Position)] =
        Some(move).filter(m => m.from.file == m.to.file && m.from.rank < m.to.rank).map(m => (m.board, m.board.pieceAt(m.from), m.from, m.to))
    }

    object Left {
      def unapply(move: ValidateMove): Option[(Board, Option[Piece], Position, Position)] =
        Some(move).filter(m => m.from.rank == m.to.rank && m.from.file > m.to.file).map(m => (m.board, m.board.pieceAt(m.from), m.from, m.to))
    }

    object Right {
      def unapply(move: ValidateMove): Option[(Board, Option[Piece], Position, Position)] =
        Some(move).filter(m => m.from.rank == m.to.rank && m.from.file < m.to.file).map(m => (m.board, m.board.pieceAt(m.from), m.from, m.to))
    }

    object UpLeft {
      def unapply(move: ValidateMove): Option[(Board, Option[Piece], Position, Position)] =
        Some(move).filter(m => m.from.rank > m.to.rank && m.from.file > m.to.file).map(m => (m.board, m.board.pieceAt(m.from), m.from, m.to))
    }

    object UpRight {
      def unapply(move: ValidateMove): Option[(Board, Option[Piece], Position, Position)] =
        Some(move).filter(m => m.from.rank > m.to.rank && m.from.file < m.to.file).map(m => (m.board, m.board.pieceAt(m.from), m.from, m.to))
    }

    object DownLeft {
      def unapply(move: ValidateMove): Option[(Board, Option[Piece], Position, Position)] =
        Some(move).filter(m => m.from.rank < m.to.rank && m.from.file > m.to.file).map(m => (m.board, m.board.pieceAt(m.from), m.from, m.to))
    }

    object DownRight {
      def unapply(move: ValidateMove): Option[(Board, Option[Piece], Position, Position)] =
        Some(move).filter(m => m.from.rank < m.to.rank && m.from.file < m.to.file).map(m => (m.board, m.board.pieceAt(m.from), m.from, m.to))
    }

  }

}

class MoveValidator extends Actor
  with QueenBehavior
  with BishopBehavior
  with RookBehavior
  with DiagonalMove
  with LinearMove
  with ActorLogging {

  protected val validatePiece: PartialFunction[(Option[Piece], Option[Piece]), Boolean] = {
    case (Some(_: White), Some(_: White)) => false
    case (Some(_: Black), Some(_: Black)) => false
    case (_, Some(_: King)) => false // Can't capture opponent's king
    case _ => true
  }

  override def receive: Receive = Queen.receive orElse Bishop.receive orElse Rook.receive orElse invalidMove

  def invalidMove: Receive = {
    case ValidateMove(board, from, to) => sendResult(board, from, to, isValid = false)
  }

  def sendResult(board: Board, from: Position, to: Position, isValid: Boolean): Unit = {
    if (isValid)
      sender ! ValidMove(board, from, to)
    else
      sender ! InvalidMove(board, from, to)
  }
}

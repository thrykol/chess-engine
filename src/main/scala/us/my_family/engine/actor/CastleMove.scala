package us.my_family.engine.actor

import us.my_family.engine.GameState.Position
import us.my_family.engine._
import us.my_family.engine.actor.MoveValidator.Moves.{Left, Right}

trait CastleMove {
  this: MoveValidator =>

  object CastleMove {

    def behavior: Receive = {
      case Left(board, piece: Some[King], from, to) =>
        lazy val firstKingMove = piece.exists(!_.moved)
        lazy val firstRookMove = board.pieceAt(Position(0, from.rank)).collect { case r: Rook => r.moved }.contains(true)
        lazy val routeClear = (1 until 4).flatMap(f => board.pieceAt(Position(f, from.rank))).isEmpty
        lazy val notCheck = false // TODO: test for check on each placement


        sendResult(board, from, to, firstKingMove && firstRookMove && routeClear && notCheck)
      case Right(board, piece: Some[King], from, to) =>
        lazy val firstKingMove = piece.exists(!_.moved)
        lazy val firstRookMove = board.pieceAt(Position(7, from.rank)).collect { case r: Rook => r.moved }.contains(true)
        lazy val routeClear = (5 until 7).flatMap(f => board.pieceAt(Position(f, from.rank))).isEmpty
        lazy val notCheck = false // TODO: test for check on each placement


        sendResult(board, from, to, firstKingMove && firstRookMove && routeClear && notCheck)
    }
  }

}

package us.my_family.engine

import us.my_family.engine.GameState.{Board, Position}
import us.my_family.engine.Piece._

object GameState {

  case class Board private(final val board: Array[Array[Option[Piece]]] = Board.initial) {

    /**
      * Return the piece at the Position
      */
    def pieceAt(position: Position): Option[Piece] = board(position.file)(position.rank)

    /**
      * Return the ranks of a file
      */
    def ranks(file: Int): Array[Option[Piece]] = board(file)

    /**
      * Return the files of a rank
      */
    def files(rank: Int): Array[Option[Piece]] = board.map { a => a(rank) }

    /**
      * Return a new board with the move applied.  The validity of the move is not tested.
      *
      * @param from Position of the piece being moved
      * @param to   Destination of the piece
      */
    def move(from: Position, to: Position): Board = {
      val board = this.board.clone()
      board(to.file)(to.rank) = board(from.file)(from.rank) map (_.move)
      board(from.file)(from.rank) = None

      this.copy(board = board)
    }

    def state: Array[Array[Option[Piece]]] = board.clone()
  }

  case class Position(file: Int, rank: Int) {
    assert(file >= 0 && file < 8, "file position off of board")
    assert(rank >= 0 && rank < 8, "rank position off of board")
  }

  object Board {

    private val initial = {

      // (0, 0) is the top left of the board while (7,7) is the bottom right
      val board = Array.fill[Option[Piece]](8, 8)(None)

      board(0)(0) = Some(Piece[Black, Rook])
      board(1)(0) = Some(Piece[Black, Knight])
      board(2)(0) = Some(Piece[Black, Bishop])
      board(3)(0) = Some(Piece[Black, Queen])
      board(4)(0) = Some(Piece[Black, King])
      board(5)(0) = Some(Piece[Black, Bishop])
      board(6)(0) = Some(Piece[Black, Knight])
      board(7)(0) = Some(Piece[Black, Rook])

      board(0)(7) = Some(Piece[White, Rook])
      board(1)(7) = Some(Piece[White, Knight])
      board(2)(7) = Some(Piece[White, Bishop])
      board(3)(7) = Some(Piece[White, Queen])
      board(4)(7) = Some(Piece[White, King])
      board(5)(7) = Some(Piece[White, Bishop])
      board(6)(7) = Some(Piece[White, Knight])
      board(7)(7) = Some(Piece[White, Rook])

      (0 to 7) foreach { index =>
        board(index)(1) = Some(Piece[Black, Pawn])
        board(6)(index) = Some(Piece[White, Pawn])
      }

      board
    }

    def apply(pieces: (Piece with Color, Position)*): Board = {
      if (pieces.nonEmpty) {
        val board = new Board(Array.fill[Option[Piece]](8, 8)(None))

        pieces foreach { case (piece, position) => board.board(position.file)(position.rank) = Some(piece) }
        board
      } else {
        new Board()
      }
    }
  }

}

case class GameState(board: Board) {

  def validate(from: Position, to: Position): Boolean = {

    board.pieceAt(from).collect {
      case _: King =>
      case _: Queen =>
      case _: Rook =>
      case _: Knight =>
      case _: Bishop =>
      case _: Pawn =>
    }.isDefined
  }
}

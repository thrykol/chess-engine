package us.my_family.engine.actor

import us.my_family.engine.Piece
import us.my_family.engine.actor.MoveValidator.Moves.{OneOnDiagonal, OneOnFile, OneOnRank}

trait SingleSquareMove {
  this: MoveValidator =>

  object SingleSquareMove {

    def behavior: Receive = {
      case OneOnFile(board, piece: Some[Piece], from, to) =>
        sendResult(board, from, to, validatePiece(piece -> board.pieceAt(to)))
      case OneOnRank(board, piece: Some[Piece], from, to) =>
        sendResult(board, from, to, validatePiece(piece -> board.pieceAt(to)))
      case OneOnDiagonal(board, piece: Some[Piece], from, to) =>
        sendResult(board, from, to, validatePiece(piece -> board.pieceAt(to)))
    }
  }

}

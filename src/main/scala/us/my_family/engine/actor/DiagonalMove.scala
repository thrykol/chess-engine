package us.my_family.engine.actor

import us.my_family.engine.GameState.Position
import us.my_family.engine.Piece
import us.my_family.engine.actor.MoveValidator.Moves.{DownLeft, DownRight, UpLeft, UpRight}

trait DiagonalMove {
  this: MoveValidator =>

  object DiagonalMove {

    def behavior: Receive = {

      case UpLeft(board, piece: Some[Piece], from, to) =>
        val distance = distanceOf(from, to)
        val route = (1 until distance) map (d => board.pieceAt(Position(from.file - d, from.rank - d))) map (p => piece -> p) map validatePiece

        sendResult(board, from, to, route.dropWhile(_ == true).isEmpty)
      case UpRight(board, piece: Some[Piece], from, to) =>
        val distance = distanceOf(from, to)
        val route = (1 until distance) map (d => board.pieceAt(Position(from.file + d, from.rank - d))) map (p => piece -> p) map validatePiece

        sendResult(board, from, to, route.dropWhile(_ == true).isEmpty)
      case DownLeft(board, piece: Some[Piece], from, to) =>
        val distance = distanceOf(from, to)
        val route = (1 until distance) map (d => board.pieceAt(Position(from.file - d, from.rank + d))) map (p => piece -> p) map validatePiece

        sendResult(board, from, to, route.dropWhile(_ == true).isEmpty)
      case DownRight(board, piece: Some[Piece], from, to) =>
        val distance = distanceOf(from, to)
        val route = (1 until distance) map (d => board.pieceAt(Position(from.file + d, from.rank + d))) map (p => piece -> p) map validatePiece

        sendResult(board, from, to, route.dropWhile(_ == true).isEmpty)
    }

    private def distanceOf(p1: Position, p2: Position): Int = Math.min(Math.abs(p1.file - p2.file), Math.abs(p1.rank - p2.rank))
  }

}

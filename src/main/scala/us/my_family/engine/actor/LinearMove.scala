package us.my_family.engine.actor

import us.my_family.engine.Piece
import us.my_family.engine.actor.MoveValidator.Moves.{Down, Left, Right, Up}

trait LinearMove {
  this: MoveValidator =>

  object LinearMove {

    def receive: Receive = {
      case Up(board, piece: Some[Piece], from, to) =>
        val ranks = board.ranks(from.file)
        val route = to.rank until from.rank map { pos => ranks(pos) } map (p => piece -> p) map validatePiece

        sendResult(board, from, to, route.dropWhile(_ == true).isEmpty)

      case Down(board, piece: Some[Piece], from, to) =>
        val ranks = board.ranks(from.file)
        val route = (from.rank + 1) to to.rank map { pos => ranks(pos) } map (p => piece -> p) map validatePiece

        sendResult(board, from, to, route.dropWhile(_ == true).isEmpty)
      case Left(board, piece: Some[Piece], from, to) =>
        val files = board.files(from.rank)
        val route = to.file until from.file map { pos => files(pos) } map (p => piece -> p) map validatePiece

        sendResult(board, from, to, route.dropWhile(_ == true).isEmpty)
      case Right(board, piece: Some[Piece], from, to) =>
        val files = board.files(from.rank)
        val route = (from.file + 1) to to.file map { pos => files(pos) } map (p => piece -> p) map validatePiece

        sendResult(board, from, to, route.dropWhile(_ == true).isEmpty)
    }
  }

}

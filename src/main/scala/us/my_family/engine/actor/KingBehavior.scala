package us.my_family.engine.actor

import us.my_family.engine.actor.MoveValidator.Pieces

trait KingBehavior {
  this: MoveValidator with SingleSquareMove with CastleMove =>

  object King {

    val isKing: Receive = {
      case Pieces.King =>
    }

    // TODO: Check for castling
    // TODO: Test for 'check'
    val moves: Receive = SingleSquareMove.behavior orElse CastleMove.behavior

    def behavior: Receive = {
      case move@Pieces.King() if moves.isDefinedAt(move) => moves(move)
    }
  }

}

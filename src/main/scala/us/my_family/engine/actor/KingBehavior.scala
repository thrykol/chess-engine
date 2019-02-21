package us.my_family.engine.actor

import us.my_family.engine.actor.MoveValidator.Pieces

trait KingBehavior {
  this: MoveValidator
    with OneRank
    with SingleSquareMove
    with CastleMove =>

  object King {

    val isKing: Receive = {
      case Pieces.King =>
    }

    // TODO: Check for castling
    // TODO: Test for 'check'
    def behavior: Receive = isKing andThen (SingleSquareMove.receive orElse CastleMove.behavior)
  }

}

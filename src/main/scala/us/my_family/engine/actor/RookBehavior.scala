package us.my_family.engine.actor

import us.my_family.engine.actor.MoveValidator.Pieces

trait RookBehavior {
  this: MoveValidator with LinearMove =>

  object Rook {

    val isRook: Receive = {
      case Pieces.Rook =>
    }

    // TODO: handle check for castling
    def receive: Receive = isRook andThen LinearMove.receive
  }

}

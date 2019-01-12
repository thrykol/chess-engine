package us.my_family.engine.actor

import us.my_family.engine
import us.my_family.engine.actor.MoveValidator.ValidateMove

trait RookBehavior {
  this: MoveValidator with LinearMove =>

  object Rook {

    val isRook: Receive = {
      case ValidateMove(_, _: Some[engine.Rook], _, _) => // no-op
    }

    // TODO: handle check for castling
    def receive: Receive = isRook andThen LinearMove.receive
  }

}

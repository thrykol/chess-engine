package us.my_family.engine.actor

import us.my_family.engine
import us.my_family.engine.actor.MoveValidator.ValidateMove

trait BishopBehavior {
  this: MoveValidator with DiagonalMove =>

  object Bishop {

    val isBishop: Receive = {
      case ValidateMove(_, _: Some[engine.Bishop], _, _) => // no-op
    }

    def receive: Receive = isBishop andThen DiagonalMove.receive
  }

}

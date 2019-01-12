package us.my_family.engine.actor

import us.my_family.engine
import us.my_family.engine.actor.MoveValidator.ValidateMove

trait QueenBehavior {
  this: MoveValidator with DiagonalMove with LinearMove =>

  object Queen {

    val isQueen: Receive = {
      case ValidateMove(_, _: Some[engine.Queen], _, _) => // no-op
    }

    def receive: Receive = isQueen andThen (DiagonalMove.receive orElse LinearMove.receive)

  }

}

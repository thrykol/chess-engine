package us.my_family.engine.actor

import us.my_family.engine.actor.MoveValidator.Pieces

trait BishopBehavior {
  this: MoveValidator with DiagonalMove =>

  object Bishop {

    val isBishop: Receive = {
      case Pieces.Bishop =>
    }

    def receive: Receive = isBishop andThen DiagonalMove.receive
  }

}

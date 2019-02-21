package us.my_family.engine.actor

import us.my_family.engine.actor.MoveValidator.Pieces

trait QueenBehavior {
  this: MoveValidator with DiagonalMove with LinearMove =>

  object Queen {

    val isQueen: Receive = {
      case Pieces.Queen =>
    }

    def receive: Receive = isQueen andThen (DiagonalMove.behavior orElse LinearMove.receive)

  }

}

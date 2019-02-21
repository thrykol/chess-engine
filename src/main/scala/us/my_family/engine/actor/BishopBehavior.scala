package us.my_family.engine.actor

import us.my_family.engine.actor.MoveValidator.Pieces

trait BishopBehavior {
  this: MoveValidator with DiagonalMove =>

  object Bishop {

    def receive: Receive = {
      case move@Pieces.Bishop() if DiagonalMove.behavior.isDefinedAt(move) => DiagonalMove.behavior(move)
    }
  }

}

package us.my_family.engine.actor

import akka.actor.{Actor, ActorLogging}

class MoveGenerator extends Actor with ActorLogging {

  override def receive: Receive = Actor.emptyBehavior
}

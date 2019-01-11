package us.my_family.engine

import akka.actor.ActorSystem

object Engine {

  def main(args: Array[String]): Unit = {

    val system = ActorSystem("chess-engine")
  }
}

package us.my_family.engine.actor

import akka.actor.{ActorRef, ActorSystem}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import us.my_family.engine.GameState.{Board, Position}
import us.my_family.engine.Piece.Bishop
import us.my_family.engine.actor.MoveValidator.{ValidMove, ValidateMove}
import us.my_family.engine.{Black, Color, Piece}

class DiagonalMoveSpec extends TestKit(ActorSystem("CastleMoveSpec")) with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {

  val be_able_to_move: AfterWord = afterWord("be able to move")

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  trait ValidatorActor {
    val actorRef: ActorRef = system.actorOf(MoveValidator.props())
    val bishop: Piece with Color = Piece[Black, Bishop]

    val from = Position(4, 4)
    val board: Board = Board(bishop -> from)
  }

  "a piece" should be_able_to_move {

    "down and right" in new ValidatorActor {

      actorRef ! ValidateMove(board, from, Position(7, 7))

      expectMsg(ValidMove(board, from, Position(7, 7)))
    }

    "down and left" in new ValidatorActor {

      actorRef ! ValidateMove(board, from, Position(0, 7))

      expectMsg(ValidMove(board, from, Position(0, 7)))
    }

    "up and right" in new ValidatorActor {

      actorRef ! ValidateMove(board, from, Position(7, 0))

      expectMsg(ValidMove(board, from, Position(7, 0)))
    }

    "up and left" in new ValidatorActor {

      actorRef ! ValidateMove(board, from, Position(0, 0))

      expectMsg(ValidMove(board, from, Position(0, 0)))
    }
  }
}
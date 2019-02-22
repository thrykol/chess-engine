package us.my_family.engine.actor

import akka.actor.{ActorRef, ActorSystem}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import us.my_family.engine.GameState.{Board, Position}
import us.my_family.engine.Piece.Bishop
import us.my_family.engine.actor.MoveValidator.{InvalidMove, ValidMove, ValidateMove}
import us.my_family.engine.{Black, Color, Piece}

class BishopBehaviorSpec extends TestKit(ActorSystem("BishopBehaviorSpec")) with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {

  val moving: AfterWord = afterWord("moving")

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  trait ValidatorActor {
    lazy val board: Board = Board(bishop -> from)
    val actorRef: ActorRef = system.actorOf(MoveValidator.props())
    val bishop: Piece with Color = Piece[Black, Bishop]

    def from: Position
  }

  trait MidBoardPosition {
    val from: Position = Position(4, 4)
  }

  "a Bishop" should {

    "be able to move" when moving {

      "down and right" in new ValidatorActor {

        val from = Position(0, 0)
        val to = Position(7, 7)

        actorRef ! ValidateMove(board, from, to)

        expectMsg(ValidMove(board, from, to))
      }

      "down and left" in new ValidatorActor {

        val from = Position(7, 0)
        val to = Position(0, 7)

        actorRef ! ValidateMove(board, from, to)

        expectMsg(ValidMove(board, from, to))
      }

      "up and left" in new ValidatorActor {

        val from = Position(7, 7)
        val to = Position(0, 0)

        actorRef ! ValidateMove(board, from, to)

        expectMsg(ValidMove(board, from, to))
      }

      "up and right" in new ValidatorActor {

        val from = Position(0, 7)
        val to = Position(7, 0)

        actorRef ! ValidateMove(board, from, to)

        expectMsg(ValidMove(board, from, to))
      }
    }

    "not be able to move" when moving {

      "up" in new ValidatorActor with MidBoardPosition {

        val to: Position = from.copy(rank = 0)

        actorRef ! ValidateMove(board, from, to)

        expectMsg(InvalidMove(board, from, to))
      }

      "down" in new ValidatorActor with MidBoardPosition {

        val to: Position = from.copy(rank = 7)

        actorRef ! ValidateMove(board, from, to)

        expectMsg(InvalidMove(board, from, to))
      }

      "left" in new ValidatorActor with MidBoardPosition {

        val to: Position = from.copy(file = 0)

        actorRef ! ValidateMove(board, from, to)

        expectMsg(InvalidMove(board, from, to))
      }

      "right" in new ValidatorActor with MidBoardPosition {

        val to: Position = from.copy(file = 7)

        actorRef ! ValidateMove(board, from, to)

        expectMsg(InvalidMove(board, from, to))
      }
    }
  }
}
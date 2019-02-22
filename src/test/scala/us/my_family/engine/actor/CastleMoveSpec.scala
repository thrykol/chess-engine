package us.my_family.engine.actor

import akka.actor.{ActorRef, ActorSystem}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import us.my_family.engine.GameState.{Board, Position}
import us.my_family.engine.Piece.{King, Rook}
import us.my_family.engine.actor.MoveValidator.{InvalidMove, ValidMove, ValidateMove}
import us.my_family.engine.{Black, Color, Piece, White}

class CastleMoveSpec extends TestKit(ActorSystem("CastleMoveSpec")) with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {

  val moving: AfterWord = afterWord("moving")
  val the_king_and_rook: AfterWord = afterWord("the king and rook")

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  trait ValidatorActor {
    val actorRef: ActorRef = system.actorOf(MoveValidator.props())

    val rook: Piece with Color = Piece[Black, Rook]
    val king: Piece with Color = Piece[Black, King]

    val pieces: Seq[(Piece with Color, Position)] = Seq(
      Piece[Black, King] -> Position(4, 0),
      Piece[Black, Rook] -> Position(0, 0),
      Piece[Black, Rook] -> Position(7, 0),
      Piece[White, King] -> Position(4, 7),
      Piece[White, Rook] -> Position(0, 7),
      Piece[White, Rook] -> Position(7, 7)
    )

    var board: Board = Board(pieces: _*)

    def flagMoved(from: Position*): Unit = {
      from foreach { p =>
        val to = Position(p.file + (if (p.file < 7) 1 else -1), p.rank)
        board = board.move(p, to)
        board = board.move(to, p)
      }
    }
  }

  "a King" should {

    "be able to castle queen side" when the_king_and_rook {

      "have not moved" in new ValidatorActor {

        actorRef ! ValidateMove(board, Position(4, 0), Position(2, 0))
        actorRef ! ValidateMove(board, Position(4, 7), Position(2, 7))

        expectMsg(ValidMove(board, Position(4, 0), Position(2, 0)))
        expectMsg(ValidMove(board, Position(4, 7), Position(2, 7)))
      }
    }

    "be able to castle king side" when the_king_and_rook {

      "have not moved" in new ValidatorActor {

        actorRef ! ValidateMove(board, Position(4, 0), Position(6, 0))
        actorRef ! ValidateMove(board, Position(4, 7), Position(6, 7))

        expectMsg(ValidMove(board, Position(4, 0), Position(6, 0)))
        expectMsg(ValidMove(board, Position(4, 7), Position(6, 7)))
      }
    }

    "be unable to castle queen side" when {

      "the king has moved" in new ValidatorActor {

        flagMoved(Position(4, 0), Position(4, 7))

        actorRef ! ValidateMove(board, Position(4, 0), Position(2, 0))
        actorRef ! ValidateMove(board, Position(4, 7), Position(2, 7))

        expectMsg(InvalidMove(board, Position(4, 0), Position(2, 0)))
        expectMsg(InvalidMove(board, Position(4, 7), Position(2, 7)))
      }

      "the rook has move" in new ValidatorActor {

        flagMoved(Position(0, 0), Position(7, 0), Position(0, 7), Position(7, 7))

        actorRef ! ValidateMove(board, Position(4, 0), Position(2, 0))
        actorRef ! ValidateMove(board, Position(4, 7), Position(2, 7))

        expectMsg(InvalidMove(board, Position(4, 0), Position(2, 0)))
        expectMsg(InvalidMove(board, Position(4, 7), Position(2, 7)))
      }

      "the king is in check" ignore {}

      "the king crosses check" ignore {}
    }

    "be unable to castle king side" when {

      "the king has moved" in new ValidatorActor {

        flagMoved(Position(4, 0), Position(4, 7))

        actorRef ! ValidateMove(board, Position(4, 0), Position(6, 0))
        actorRef ! ValidateMove(board, Position(4, 7), Position(6, 7))

        expectMsg(InvalidMove(board, Position(4, 0), Position(6, 0)))
        expectMsg(InvalidMove(board, Position(4, 7), Position(6, 7)))
      }

      "the rook has move" in new ValidatorActor {

        flagMoved(Position(0, 0), Position(7, 0), Position(0, 7), Position(7, 7))

        actorRef ! ValidateMove(board, Position(4, 0), Position(6, 0))
        actorRef ! ValidateMove(board, Position(4, 7), Position(6, 7))

        expectMsg(InvalidMove(board, Position(4, 0), Position(6, 0)))
        expectMsg(InvalidMove(board, Position(4, 7), Position(6, 7)))
      }

      "the king is in check" ignore {}

      "the king crosses check" ignore {}
    }
  }

}

package us.my_family.engine

import scala.reflect.runtime.universe._

sealed trait Piece {
  def symbol: Option[Char]

  def value: Option[Int]
}

sealed trait Color

object Piece {

  def apply[C <: Color : TypeTag, P <: Piece : TypeTag]: Piece with Color = (typeOf[C], typeOf[P]) match {
    case (c, p) if c =:= typeOf[White] && p =:= typeOf[King] => new King() with White
    case (c, p) if c =:= typeOf[Black] && p =:= typeOf[King] => new King() with Black
    case (c, p) if c =:= typeOf[White] && p =:= typeOf[Queen] => new Queen() with White
    case (c, p) if c =:= typeOf[Black] && p =:= typeOf[Queen] => new Queen() with Black
    case (c, p) if c =:= typeOf[White] && p =:= typeOf[Rook] => new Rook() with White
    case (c, p) if c =:= typeOf[Black] && p =:= typeOf[Rook] => new Rook() with Black
    case (c, p) if c =:= typeOf[White] && p =:= typeOf[Knight] => new Knight() with White
    case (c, p) if c =:= typeOf[Black] && p =:= typeOf[Knight] => new Knight() with Black
    case (c, p) if c =:= typeOf[White] && p =:= typeOf[Bishop] => new Bishop() with White
    case (c, p) if c =:= typeOf[Black] && p =:= typeOf[Bishop] => new Bishop() with Black
    case (c, p) if c =:= typeOf[White] && p =:= typeOf[Pawn] => new Pawn() with White
    case (c, p) if c =:= typeOf[Black] && p =:= typeOf[Pawn] => new Pawn() with Black
  }
}

trait White extends Color

trait Black extends Color

case class King private() extends Piece {
  val symbol = Some('K')
  val value: Option[Int] = None
}

case class Queen private() extends Piece {
  val symbol = Some('Q')
  val value = Some(9)
}

case class Rook private() extends Piece {
  val symbol = Some('R')
  val value = Some(5)
}

case class Knight private() extends Piece {
  val symbol = Some('N')
  val value = Some(3)
}

case class Bishop private() extends Piece {
  val symbol = Some('B')
  val value = Some(3)
}

case class Pawn private() extends Piece {
  val symbol: Option[Char] = None
  val value = Some(1)
}
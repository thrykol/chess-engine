package us.my_family.engine

import scala.reflect.runtime.universe._

sealed trait Piece {
  /**
    * Indicates whether or not the piece has been moved
    */
  val moved: Boolean = false

  def symbol: Option[Char]

  def value: Option[Int]

  def move: Piece with Color = {
    this match {
      case _: King with Black => Piece[Black, King](hasMoved = true)
      case _: King with White => Piece[White, King](hasMoved = true)
      case _: Queen with Black => Piece[Black, Queen](hasMoved = true)
      case _: Queen with White => Piece[White, Queen](hasMoved = true)
      case _: Rook with Black => Piece[Black, Rook](hasMoved = true)
      case _: Rook with White => Piece[White, Rook](hasMoved = true)
      case _: Knight with Black => Piece[Black, Knight](hasMoved = true)
      case _: Knight with White => Piece[White, Knight](hasMoved = true)
      case _: Bishop with Black => Piece[Black, Bishop](hasMoved = true)
      case _: Bishop with White => Piece[White, Bishop](hasMoved = true)
      case _: Pawn with Black => Piece[Black, Pawn](hasMoved = true)
      case _: Pawn with White => Piece[White, Pawn](hasMoved = true)
    }
  }
}

sealed trait Color

object Piece {

  def apply[C <: Color : TypeTag, P <: Piece : TypeTag]: Piece with Color = apply(false)

  def apply[C <: Color : TypeTag, P <: Piece : TypeTag](hasMoved: Boolean): Piece with Color = (typeOf[C], typeOf[P]) match {
    case (c, p) if c =:= typeOf[White] && p =:= typeOf[King] => new King(hasMoved) with White
    case (c, p) if c =:= typeOf[Black] && p =:= typeOf[King] => new King(hasMoved) with Black
    case (c, p) if c =:= typeOf[White] && p =:= typeOf[Queen] => new Queen(hasMoved) with White
    case (c, p) if c =:= typeOf[Black] && p =:= typeOf[Queen] => new Queen(hasMoved) with Black
    case (c, p) if c =:= typeOf[White] && p =:= typeOf[Rook] => new Rook(hasMoved) with White
    case (c, p) if c =:= typeOf[Black] && p =:= typeOf[Rook] => new Rook(hasMoved) with Black
    case (c, p) if c =:= typeOf[White] && p =:= typeOf[Knight] => new Knight(hasMoved) with White
    case (c, p) if c =:= typeOf[Black] && p =:= typeOf[Knight] => new Knight(hasMoved) with Black
    case (c, p) if c =:= typeOf[White] && p =:= typeOf[Bishop] => new Bishop(hasMoved) with White
    case (c, p) if c =:= typeOf[Black] && p =:= typeOf[Bishop] => new Bishop(hasMoved) with Black
    case (c, p) if c =:= typeOf[White] && p =:= typeOf[Pawn] => new Pawn(hasMoved) with White
    case (c, p) if c =:= typeOf[Black] && p =:= typeOf[Pawn] => new Pawn(hasMoved) with Black
  }
}

trait White extends Color

trait Black extends Color

case class King private(override val moved: Boolean) extends Piece {
  val symbol = Some('K')
  val value: Option[Int] = None
}

case class Queen private(override val moved: Boolean) extends Piece {
  val symbol = Some('Q')
  val value = Some(9)
}

case class Rook private(override val moved: Boolean) extends Piece {
  val symbol = Some('R')
  val value = Some(5)
}

case class Knight private(override val moved: Boolean) extends Piece {
  val symbol = Some('N')
  val value = Some(3)
}

case class Bishop private(override val moved: Boolean) extends Piece {
  val symbol = Some('B')
  val value = Some(3)
}

case class Pawn private(override val moved: Boolean) extends Piece {
  val symbol: Option[Char] = None
  val value = Some(1)
}
package us.my_family.engine

sealed trait Piece {
  def symbol: Option[Char]

  def value: Option[Int]
}

case object King extends Piece {
  val symbol = Some('K')
  val value: Option[Int] = None
}

case object Queen extends Piece {
  val symbol = Some('Q')
  val value = Some(9)
}

case object Rook extends Piece {
  val symbol = Some('R')
  val value = Some(5)
}

case object Knight extends Piece {
  val symbol = Some('N')
  val value = Some(3)
}

case object Bishop extends Piece {
  val symbol = Some('B')
  val value = Some(3)
}

case object Pawn extends Piece {
  val symbol: Option[Char] = None
  val value = Some(1)
}
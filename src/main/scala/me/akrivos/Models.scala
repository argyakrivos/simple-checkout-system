package me.akrivos

object Item {
  private val all = Set(Apple, Orange).map(x => (x.name, x)).toMap
  def exists(str: String): Boolean = all.contains(str)
  def apply(str: String): Item = all(str)
}

sealed trait Item {
  def name: String
  def price: BigDecimal
}

case object Apple extends Item {
  val name = "Apple"
  val price: BigDecimal = 0.6
}

case object Orange extends Item {
  val name = "Orange"
  val price: BigDecimal = 0.25
}

case class Basket(items: List[Item])

case class Receipt(items: List[Item], total: BigDecimal)

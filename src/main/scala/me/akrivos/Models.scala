package me.akrivos

sealed trait Item {
  def price: BigDecimal
}

case object Apple extends Item {
  def price: BigDecimal = 0.6
}

case class Basket(items: List[Item])

case class Receipt(items: List[Item], total: BigDecimal)

package me.akrivos

sealed trait Item

case class Basket(items: List[Item])

case class Receipt(items: List[Item], total: BigDecimal)

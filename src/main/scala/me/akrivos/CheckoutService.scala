package me.akrivos

import scala.concurrent.{ExecutionContext, Future}

trait CheckoutService {
  def checkout(basket: Basket): Future[Receipt]
}

class DefaultCheckoutService(implicit ec: ExecutionContext) extends CheckoutService {

  override def checkout(basket: Basket) = Future {
    val total = basket.items.map(_.price).sum
    Receipt(basket.items, total)
  }
}

package me.akrivos

import scala.concurrent.{ExecutionContext, Future}

trait CheckoutService {
  def checkout(basket: Basket): Future[Receipt]
}

class DefaultCheckoutService(implicit ec: ExecutionContext) extends CheckoutService {

  override def checkout(basket: Basket) = Future {
    val total = basket.items.map(_.price).sum
    val savings = getOfferSavings(basket)
    Receipt(basket.items, total - savings)
  }

  private def getOfferSavings(basket: Basket): BigDecimal = {
    val items = basket.items.groupBy(x => x).map(x => (x._1, x._2.size))

    // buy one, get one free on Apples
    items.get(Apple).filter(_ >= 2).map { count =>
      val total = count * Apple.price
      val offer = ((count / 2) * Apple.price) + ((count % 2) * Apple.price)
      total - offer
    }.getOrElse(0)
  }
}

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
    // buy one, get one free on Apples
    val appleSavings = basket.items.count(_ == Apple) / 2 * Apple.price

    // 3 for the price of 2 on Oranges
    val orangeSavings = basket.items.count(_ == Orange) / 3 * Orange.price

    appleSavings + orangeSavings
  }
}

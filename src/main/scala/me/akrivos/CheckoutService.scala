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
    // buy one, get one free on Apples and/or Bananas
    val buyOneGetOneFreeSavings = {
      val items = basket.items.filter(x => x == Banana || x == Apple)
      if (items.length > 1)
        items.sortBy(_.price).take(items.length / 2).map(_.price).sum
      else
        BigDecimal(0)
    }

    // 3 for the price of 2 on Oranges
    val orangeSavings = basket.items.count(_ == Orange) / 3 * Orange.price

    buyOneGetOneFreeSavings + orangeSavings
  }
}

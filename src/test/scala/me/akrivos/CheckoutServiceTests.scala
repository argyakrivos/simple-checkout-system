package me.akrivos

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FlatSpecLike, Matchers}
import scala.concurrent.ExecutionContext.Implicits.global

class CheckoutServiceTests extends FlatSpecLike with ScalaFutures with Matchers {

  val service: CheckoutService = new DefaultCheckoutService()

  behavior of "The checkout system"

  it should "give a receipt of £0.6 for an Apple" in {
    val basket = Basket(List(Apple))
    whenReady(service.checkout(basket)) { r =>
      r shouldBe Receipt(basket.items, 0.6)
    }
  }
}

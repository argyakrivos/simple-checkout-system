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

  it should "give a receipt of £0.25 for an Orange" in {
    val basket = Basket(List(Orange))
    whenReady(service.checkout(basket)) { r =>
      r shouldBe Receipt(basket.items, 0.25)
    }
  }

  it should "give a receipt of £0.85 for: Orange, Apple" in {
    val basket = Basket(List(Orange, Apple))
    whenReady(service.checkout(basket)) { r =>
      r shouldBe Receipt(basket.items, 0.85)
    }
  }

  it should "give a receipt of £1.45 for: Apple, Apple, Orange, Apple" in {
    val basket = Basket(List(Apple, Apple, Orange, Apple))
    whenReady(service.checkout(basket)) { r =>
      r shouldBe Receipt(basket.items, 1.45)
    }
  }

  it should "apply Apple offer for 2 Apples (£0.6)" in {
    val basket = Basket(List.fill(2)(Apple))
    whenReady(service.checkout(basket)) { r =>
      r shouldBe Receipt(basket.items, 0.6)
    }
  }

  it should "apply Apple offer for 4 Apples (£1.2)" in {
    val basket = Basket(List.fill(4)(Apple))
    whenReady(service.checkout(basket)) { r =>
      r shouldBe Receipt(basket.items, 1.2)
    }
  }

  it should "apply Apple offer for 5 Apples (£1.8)" in {
    val basket = Basket(List.fill(5)(Apple))
    whenReady(service.checkout(basket)) { r =>
      r shouldBe Receipt(basket.items, 1.8)
    }
  }
}

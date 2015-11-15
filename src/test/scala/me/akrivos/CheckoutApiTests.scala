package me.akrivos

import org.scalatest.{FlatSpecLike, Matchers}
import spray.http.StatusCodes._
import spray.routing.HttpService
import spray.testkit.ScalatestRouteTest

class CheckoutApiTests extends FlatSpecLike with Matchers with ScalatestRouteTest with HttpService {

  val actorRefFactory = system
  val routes = new CheckoutApi(new DefaultCheckoutService()).routes

  behavior of "The checkout API"

  it should "return 400 if it is not given a basket" in {
    Post("/checkout") ~> sealRoute(routes) ~> check {
      status shouldBe BadRequest
    }
  }
}

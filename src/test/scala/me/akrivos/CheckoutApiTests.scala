package me.akrivos

import org.json4s._
import org.scalatest.{FlatSpecLike, Matchers}
import spray.http.StatusCodes._
import spray.routing.HttpService
import spray.testkit.ScalatestRouteTest

class CheckoutApiTests extends FlatSpecLike with Matchers with ScalatestRouteTest with HttpService with JsonSupport {

  val actorRefFactory = system
  val routes = new CheckoutApi(new DefaultCheckoutService()).routes

  behavior of "The checkout API"

  it should "return 400 if it is not given a basket" in {
    Post("/checkout") ~> sealRoute(routes) ~> check {
      status shouldBe BadRequest
    }
  }

  it should "return 200 and a receipt of £0.6 for an Apple" in {
    val json = JObject("items" -> JArray(List(JString("Apple"))))
    Post("/checkout", json) ~> routes ~> check {
      status shouldBe OK
      responseAs[Receipt] shouldBe Receipt(List(Apple), 0.6)
    }
  }

  it should "return 200 and a receipt of £0.25 for an Orange" in {
    val json = JObject("items" -> JArray(List(JString("Orange"))))
    Post("/checkout", json) ~> routes ~> check {
      status shouldBe OK
      responseAs[Receipt] shouldBe Receipt(List(Orange), 0.25)
    }
  }

  it should "return 400 if an item in the basket is unrecognised" in {
    val json = JObject("items" -> JArray(List(JString("XYZ"))))
    Post("/checkout", json) ~> sealRoute(routes) ~> check {
      status shouldBe BadRequest
    }
  }

  it should "return 200 with a receipt of £0.85 for an Orange and an Apple" in {
    val json = JObject("items" -> JArray(List(JString("Orange"), JString("Apple"))))
    Post("/checkout", json) ~> routes ~> check {
      status shouldBe OK
      responseAs[Receipt] shouldBe Receipt(List(Orange, Apple), 0.85)
    }
  }

  it should "return 400 if the basket is empty" in {
    val json = JObject("items" -> JArray(List.empty))
    Post("/checkout", json) ~> sealRoute(routes) ~> check {
      status shouldBe BadRequest
    }
  }
}

package me.akrivos

import akka.actor.ActorRefFactory
import spray.routing.HttpService

import scala.concurrent.ExecutionContext

class CheckoutApi(service: CheckoutService)(implicit val actorRefFactory: ActorRefFactory, ec: ExecutionContext)
  extends HttpService with JsonSupport {

  val routes = post {
    path("checkout") {
      entity(as[Basket]) { basket =>
        onSuccess(service.checkout(basket)) { receipt =>
          complete(receipt)
        }
      }
    }
  }
}

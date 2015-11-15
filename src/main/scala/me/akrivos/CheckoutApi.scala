package me.akrivos

import akka.actor.ActorRefFactory
import spray.http.StatusCodes
import spray.routing.HttpService

import scala.concurrent.ExecutionContext

class CheckoutApi(service: CheckoutService)(implicit val actorRefFactory: ActorRefFactory, ec: ExecutionContext)
  extends HttpService {

  val routes = complete(StatusCodes.OK)
}

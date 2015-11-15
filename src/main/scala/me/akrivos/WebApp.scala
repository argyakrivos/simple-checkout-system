package me.akrivos

import java.util.concurrent.TimeUnit

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import spray.can.Http
import spray.routing.HttpServiceActor

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

object WebApp extends App {

  implicit val system = ActorSystem("checkout-system")
  implicit val ec = system.dispatcher
  sys.addShutdownHook(system.terminate())

  val config = ConfigFactory.load()
  implicit val timeout = Timeout(config.getDuration("api.timeout", TimeUnit.SECONDS).seconds)

  val service = system.actorOf(Props(new WebService()), "web-service")
  IO(Http) ! Http.Bind(service, config.getString("api.host"), port = config.getInt("api.port"))
}

class WebService(implicit system: ActorSystem, ec: ExecutionContext) extends HttpServiceActor {

  val checkoutService = new DefaultCheckoutService()
  val api = new CheckoutApi(checkoutService)

  override def receive = runRoute(api.routes)
}

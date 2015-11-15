package me.akrivos

import org.json4s.JsonAST.JString
import org.json4s.{CustomSerializer, DefaultFormats}
import spray.httpx.Json4sJacksonSupport

trait JsonSupport extends Json4sJacksonSupport {

  object ItemSerialiser extends CustomSerializer[Item](format => (
    { case JString(s) if Item.exists(s) => Item(s) },
    { case x: Item => JString(x.name) }
  ))

  override implicit val json4sJacksonFormats = DefaultFormats + ItemSerialiser
}

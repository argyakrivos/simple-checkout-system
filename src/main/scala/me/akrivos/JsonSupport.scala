package me.akrivos

import org.json4s.DefaultFormats
import spray.httpx.Json4sJacksonSupport

trait JsonSupport extends Json4sJacksonSupport {

  override implicit val json4sJacksonFormats = DefaultFormats
}

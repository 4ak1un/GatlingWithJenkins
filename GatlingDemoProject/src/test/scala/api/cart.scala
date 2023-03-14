package api

import config.BaseHelpers._
import io.gatling.core.Predef._
import io.gatling.core.structure._
import io.gatling.http.Predef._

object cart {
  def openCart() = {
    exec(http("Open Cart")
      .get(mainPage + "/cart"))
  }

  def getTokenDefault() = {
    exec(
      http("${token}?store=DEFAULT")
        .get(mainPageApi + "/cart/${token}?store=DEFAULT")
    )
  }
  
}

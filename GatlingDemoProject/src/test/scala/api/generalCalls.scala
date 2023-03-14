package api

import config.BaseHelpers._
import io.gatling.core.Predef._
import io.gatling.core.structure._
import io.gatling.http.Predef._

object generalCalls {

  def ping(): ChainBuilder = {
    exec(
      http("ping")
        .get(pingUrl)
        .check(jsonPath("$.status").is("UP"))
    )
  }

  def lang(): ChainBuilder = {
    exec(
      http("?lang=en")
        .get(mainPageApi + "/content/boxes/headerMessage/?lang=en")
        .check(status.is(404))
    )
  }

  def price(productPrice: Int = 1): ChainBuilder = {
    exec(
      http("price/")
        .post(mainPageApi + s"/product/${productPrice}/price/")
        .body(StringBody("""{"options":[]}""")).asJson
    )
  }

  def storeDefault(): ChainBuilder = {
    exec(
      http("DEFAULT")
        .get(mainPageApi + "/store/DEFAULT")
        .check(jsonPath("$.name").is("Default store"))
    )
  }

  def requestProducts(requestUrl: String): ChainBuilder = {
    exec(
      http(requestUrl)
        .get(mainPageApi + "/products/" + requestUrl)
    )
  }

  def requestCategory(requestUrl: String, statusCode: Int = 200): ChainBuilder = {
    exec(
      http(requestUrl)
        .get(mainPageApi + "/category/" + requestUrl)
        .check(status.is(statusCode))
    )
  }

  def requestContent(requestUrl: String): ChainBuilder = {
    exec(
      http(requestUrl)
        .get(mainPageApi + "/content/" + requestUrl)
    )
  }

}

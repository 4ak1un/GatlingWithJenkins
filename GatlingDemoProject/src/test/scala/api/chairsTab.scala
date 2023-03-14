package api

import config.BaseHelpers._
import io.gatling.core.Predef._
import io.gatling.core.structure._
import io.gatling.http.Predef._

object chairsTab {

  val feeder = csv("src/test/resources/feeders/chairTypes.csv").random()

  def openChairsTab(): ChainBuilder = {
    exec(
      http("chairs")
        .get(mainPage + "/category/chairs")
    )
  }

  def openRandomChair() = {
    feed(feeder)
      .exec(http("${chairTypes}")
        .get(mainPage + "/product/${chairTypes}")
      )
  }

  def addChairToCart() = {
    feed(feeder)
      .exec(http("Add chair to cart")
        .put(mainPageApi + "/cart/${token}?store=DEFAULT")
        .header("accept", "application/json")
        .header("content-type", "application/json")
        .body(StringBody("{ \"attributes\": [], \"product\": \"${sku}\", \"quantity\": 1 }"))
        .check(jsonPath("$.products").exists))
  }
}

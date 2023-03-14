package api
import config.BaseHelpers._
import io.gatling.core.Predef._
import io.gatling.core.structure._
import io.gatling.http.Predef._

object checkout {
  def openCheckout(): ChainBuilder = {
    exec(
      http("checkout")
        .get(mainPage + "/checkout")
    )
  }

  def getCountry(): ChainBuilder = {
    exec(
      http("/country?store=DEFAULT&lang=en")
        .get("http://localhost:8080/api/v1/shipping/country?store=DEFAULT&lang=en")
    )
  }

  def getZones(): ChainBuilder = {
    exec(
      http("zones/?code=")
        .get("http://localhost:8080/api/v1/zones/?code=")
    )
  }

  def getConfig(): ChainBuilder = {
    exec(
      http("config/")
        .get(mainPageApi + "/config/")
    )
  }

  def getTotal(): ChainBuilder = {
    exec(
      http("total/")
        .get(mainPageApi + "/cart/${token}/total/")
    )
  }

  def shipping() = {
    exec(http("shipping")
      .post(mainPageApi + "/cart/${token}/shipping")
      .check(status.is(503))
      .header("accept", "application/json")
      .header("content-type", "application/json")
      .body(StringBody("{}")))
  }

  def maps(): ChainBuilder = {
    exec(
      http("gen_204?csp_test=true")
        .get("https://maps.googleapis.com/maps/api/mapsjs/gen_204?csp_test=true")
    )
  }

}

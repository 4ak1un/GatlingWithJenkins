package api

import config.BaseHelpers._
import io.gatling.core.Predef._
import io.gatling.core.structure._
import io.gatling.http.Predef._

object tableTab {
  def openOliveTableTab(): ChainBuilder = {
    exec(
      http("olive-table")
        .get(mainPage + "/product/olive-table")
    )
  }

  def addTableToCart() = {
    exec(http("Add table to Cart")
      .post(mainPageApi + "/cart/?store=DEFAULT")
      .check(status.is(201))
      .header("accept", "application/json")
      .header("content-type", "application/json")
      .body(StringBody("{ \"attributes\": [], \"product\": \"table1\", \"quantity\": 1 }"))
      .check(jsonPath("$.id").exists)
      .check(jsonPath("$.subtotal").is("199.0"))
      .check(jsonPath("$.quantity").is("1"))
      .check(jsonPath("$.code").saveAs("token")))
  }
  def getToken() = {
    exec(
      http("${token}?lang=en")
        .get(mainPageApi + "/cart/${token}?lang=en")
    )
  }
}

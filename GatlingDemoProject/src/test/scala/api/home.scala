package api

import config.BaseHelpers._
import io.gatling.core.Predef._
import io.gatling.core.structure._
import io.gatling.http.Predef._

object home {

  def shopizerHome(): ChainBuilder = {
    exec(
      http("localhost")
        .get(mainPage)
    )
  }
}
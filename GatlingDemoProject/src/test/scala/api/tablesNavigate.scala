package api

import config.BaseHelpers._
import io.gatling.core.Predef._
import io.gatling.core.structure._
import io.gatling.http.Predef._

object tablesNavigate {
  def openTablesTab(): ChainBuilder = {
    exec(
      http("tables")
        .get(mainPage + "/category/tables")
    )
  }
}

package config

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.core.structure._
import io.gatling.http.protocol.HttpProtocolBuilder

object BaseHelpers {
  val mainPage = "http://localhost"
  val mainPageApi = "http://localhost:8080/api/v1"
  val pingUrl = "http://localhost:8080/actuator/health/ping"

  def thinkTimer(Min :Int = 2, Max :Int = 5): ChainBuilder = {
    pause(Min,Max)
  }

  val httpProtocol: HttpProtocolBuilder = http //
    .acceptHeader("*/*") // 6
    .upgradeInsecureRequestsHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")
}

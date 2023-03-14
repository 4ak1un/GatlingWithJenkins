package simulations

import config.BaseHelpers.httpProtocol
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.core.structure._
import io.gatling.core.scenario._
import scenarios.ShopizerDemo.scnShopizerDemo

class PerfTestSimulation extends Simulation{

val usersCount = System.getProperty("ShopizerDemoUsers", "1").toInt

  setUp(
    scnShopizerDemo.inject(atOnceUsers(usersCount))
  ).protocols(httpProtocol)
}

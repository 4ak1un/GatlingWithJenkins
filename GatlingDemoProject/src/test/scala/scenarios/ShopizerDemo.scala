package scenarios

import api.generalCalls._
import api.home._
import api.tablesNavigate._
import api.tableTab._
import api.chairsTab._
import api.cart._
import api.checkout._
import config.BaseHelpers._
import io.gatling.core.Predef._
import io.gatling.core.structure._
import io.gatling.http.Predef._

object ShopizerDemo {

  val minPause = System.getProperty("minPause", "2").toInt
  val maxPause = System.getProperty("maxPause", "5").toInt

  val chairBuyersPercent = System.getProperty("chairBuyersPercent", "50.0").toDouble
  val checkoutPercent = System.getProperty("checkoutPercent", "30.0").toDouble

  def scnShopizerDemo: ScenarioBuilder = {
    scenario("Shopizer performance testing")
      .exec(flushHttpCache)
      .exec(flushCookieJar)
      .exitBlockOnFail(

        group("Open the application"){
          exec(shopizerHome())
            .exec(ping())
            .exec(lang())
            .exec(requestProducts("group/FEATURED_ITEM?store=DEFAULT&lang=en"))
            .exec(storeDefault())
            .exec(requestCategory("?count=20&page=0&store=DEFAULT&lang=en"))
            .exec(requestContent("pages/?page=0&count=20&store=DEFAULT&lang=en"))
            .exec(price(50))
            .exec(price(1))
            .exec(price(51))
            .exec(price(52))
            .exec(price(50))
            .exec(price(1))
            .exec(price(51))
            .exec(price(52))
            .exec(thinkTimer(minPause,maxPause))
        }
        .group("Navigate to \"Tables\" tab"){
          exec(openTablesTab())
            .exec(ping())
            .exec(lang())
            .exec(requestProducts("?&store=DEFAULT&lang=en&page=0&count=15&category=50"))
            .exec(storeDefault())
            .exec(requestCategory("?count=20&page=0&store=DEFAULT&lang=en"))
            .exec(requestContent("pages/?page=0&count=20&store=DEFAULT&lang=en"))
            .exec(price(1))
            .exec(requestCategory("50?store=DEFAULT&lang=en"))
            .exec(requestCategory("50/manufacturers/?store=DEFAULT&lang=en"))
            .exec(requestCategory("50/variants/?store=DEFAULT&lang=en", 404))
            .exec(thinkTimer(minPause,maxPause))
        }
          .group("Open a table product card"){
            exec(openOliveTableTab())
              .exec(ping())
              .exec(requestProducts("1?lang=en&store=DEFAULT"))
              .exec(requestProducts("1/reviews?store=DEFAULT"))
              .exec(lang())
              .exec(storeDefault())
              .exec(requestCategory("?count=20&page=0&store=DEFAULT&lang=en"))
              .exec(requestContent("pages/?page=0&count=20&store=DEFAULT&lang=en"))
              .exec(price(1))
              .exec(thinkTimer(minPause,maxPause))
          }
          .group("Add table to Cart"){
            exec(addTableToCart())
              .exec(getToken())
              .exec(thinkTimer(minPause,maxPause))
          }

          // -------------------50% of users -------------------------------------
          .randomSwitch(chairBuyersPercent ->
            group("Click \"Chairs tab\""){
              exec(openChairsTab())
                .exec(ping())
                .exec(lang())
                .exec(requestProducts("?&store=DEFAULT&lang=en&page=0&count=15&category=51"))
                .exec(requestCategory("?count=20&page=0&store=DEFAULT&lang=en"))
                .exec(storeDefault())
                .exec(requestContent("pages/?page=0&count=20&store=DEFAULT&lang=en"))
                .exec(price(51))
                .exec(price(50))
                .exec(price(52))
                .exec(requestCategory("51?store=DEFAULT&lang=en"))
                .exec(requestCategory("51/manufacturers/?store=DEFAULT&lang=en"))
                .exec(requestCategory("51/variants/?store=DEFAULT&lang=en",404))
                .exec(thinkTimer(minPause,maxPause))
            }
              .group("Open random chair"){
                exec(openRandomChair())
                  .exec(ping())
                  .exec(lang())
                  .exec(requestProducts("51/reviews?store=DEFAULT"))
                  .exec(requestProducts("51?lang=en&store=DEFAULT"))
                  .exec(requestCategory("?count=20&page=0&store=DEFAULT&lang=en"))
                  .exec(requestContent("pages/?page=0&count=20&store=DEFAULT&lang=en"))
                  .exec(storeDefault())
                  .exec(price(51))
                  .exec(thinkTimer(minPause,maxPause))
              }
              .group("Add chair to Cart") {
                exec(addChairToCart())
                  .exec(getToken())
                  .exec(thinkTimer(minPause, maxPause))
              }
          )
          // --------------30% of users--------------------------------------------
          .randomSwitch(checkoutPercent -> exec(
             group("Open Cart"){
               exec(openCart())
                 .exec(lang())
                 .exec(getTokenDefault())
                 .exec(getTokenDefault())
                 .exec(ping())
                 .exec(storeDefault())
                 .exec(requestCategory("?count=20&page=0&store=DEFAULT&lang=en"))
                 .exec(requestContent("pages/?page=0&count=20&store=DEFAULT&lang=en"))
             }
               .group ("Click proceed to checkout") {
               exec(openCheckout())
                 .exec(ping())
                 .exec(getCountry())
                 .exec(getTokenDefault())
                 .exec(getZones())
                 .exec(lang())
                 .exec(getConfig())
                 .exec(getZones())
                 .exec(getTotal())
                 .exec(shipping())
                 .exec(storeDefault())
                 .exec(requestCategory("?count=20&page=0&store=DEFAULT&lang=en"))
                 .exec(requestContent("pages/?page=0&count=20&store=DEFAULT&lang=en"))
                 .exec(maps())
             }
          )
        )



      )
  }

}

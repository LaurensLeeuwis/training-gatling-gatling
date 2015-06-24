package eu.leeuwis.training.gatling

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class AdjustedRecordedSimulation extends Simulation {

  val httpProtocol = http
    .baseURL("http://localhost:8080")
    .inferHtmlResources()
    .acceptHeader("application/json, text/plain, */*")
    .acceptEncodingHeader("gzip, deflate, sdch")
    .acceptLanguageHeader("en-US,en;q=0.8,nl;q=0.6")
    .contentTypeHeader("application/json;charset=UTF-8")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.130 Safari/537.36")

  val headers_0 = Map("Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")

  val headers_1 = Map("Accept" -> "text/css,*/*;q=0.1")

  val headers_2 = Map("Accept" -> "*/*")

  val headers_7 = Map(
    "Accept" -> "*/*",
    "X-Chrome-UMA-Enabled" -> "1",
    "X-Client-Data" -> "CJe2yQEIorbJAQiptskBCMS2yQEI64jKAQ==")

  val headers_9 = Map(
    "Accept" -> "*/*",
    "Pragma" -> "no-cache")

  val headers_11 = Map(
    "Accept-Encoding" -> "gzip, deflate",
    "Origin" -> "http://localhost:8080")

  val uri1 = "http://localhost:8080"
  val uri2 = "ajax.googleapis.com"

  val scn = scenario("RecordedSimulation")
    .exec(http("Angular App")
    .get("/index.html")
    .headers(headers_0)
    .resources(http("request_1")
    .get(uri1 + "/styles/main.css")
    .headers(headers_1)
    .check(status.is(200)),
      http("Angular App - Javascript")
        .get(uri1 + "/scripts/controllers/secret.js")
        .headers(headers_2)
        .check(status.is(200)),
      http("Angular App - Javascript")
        .get(uri1 + "/scripts/lib/angular.min.js")
        .headers(headers_2)
        .check(status.is(200)),
      http("Angular App - Javascript")
        .get(uri1 + "/scripts/app.js")
        .headers(headers_2)
        .check(status.is(200)),
      http("Angular App - Javascript")
        .get(uri1 + "/scripts/controllers/login.js")
        .headers(headers_2)
        .check(status.is(200)),
      http("Angular App - Javascript")
        .get(uri1 + "/scripts/controllers/main.js")
        .headers(headers_2)
        .check(status.is(200)),
      http("Angular App - Javascript")
        .get("http://" + uri2 + "/ajax/libs/angularjs/1.4.1/angular-route.js")
        .headers(headers_7)
        .check(status.is(200)),
      http("Angular App - Views")
        .get(uri1 + "/views/main.html")
        .check(status.is(200)),
      http("request_9")
        .get(uri1 + "/favicon.ico")
        .headers(headers_9),
      http("request_10")
        .get(uri1 + "/echo?message=echooo"))
    .check(status.is(200)))
    .pause(12)
    .exec(http("request_11")
    .post("/login")
    .headers(headers_11)
    .body(RawFileBody("ExampleRecordedSimulation_0011_request.txt"))
    .check(status.is(401)))
    .pause(1)
    .exec(http("request_12")
    .get("/my")
    .check(status.is(403)))
    .pause(1)
    .exec(http("Simple pages")
    .get("/simplepages/index.html")
    .headers(headers_0)
    .check(status.is(200)))
    .pause(1)
    .exec(http("Simple pages")
    .get("/simplepages/1.html")
    .headers(headers_0)
    .check(status.is(200)))
    .pause(1)
    .exec(http("Simple pages")
    .get("/simplepages/2.html")
    .headers(headers_0)
    .check(status.is(200)))
    .pause(1)
    .exec(http("Simple pages")
    .get("/simplepages/3.html")
    .headers(headers_0)
    .check(status.is(200)))
    .pause(1)
    .exec(http("request_17")
    .get("/echo?message=echooo"))
    .pause(1)
    .exec(http("request_18")
    .get("/my")
    .check(status.is(403)))
    .pause(8)
    .exec(http("request_19")
    .post("/login")
    .headers(headers_11)
    .body(RawFileBody("ExampleRecordedSimulation_0019_request.txt"))
    .resources(http("request_20")
    .get(uri1 + "/echo?message=echooo"))
    .check(status.is(401)))

  val testdata = jsonFile("testdata.json").random

  val myScenario = scenario("echoTestData").feed(testdata).exec(
    http("echoo").get(uri1 + "/echo").queryParam("message", "${testdata}")
  )

  val mySlowScenario = scenario("captain slow").exec(
    http("slow").get(uri1 + "/slow")
  )

  setUp(mySlowScenario.inject(atOnceUsers(100))).protocols(httpProtocol)
//  setUp(myScenario.inject(atOnceUsers(20))).protocols(httpProtocol)

//  setUp(
//    scn.inject(atOnceUsers(100))
//  ).protocols(httpProtocol)
}
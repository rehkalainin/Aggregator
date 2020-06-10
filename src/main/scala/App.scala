import java.nio.file.{Path, Paths}
import java.util.concurrent.TimeUnit

import akka.{Done, NotUsed}
import akka.actor.{ActorSystem, CoordinatedShutdown}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.Uri
import akka.stream.scaladsl.Source
import models.SiteDataModel
import services.ParsingCsvService.parsingStreetsCsv
import services.Agregator
import spray.json.JsValue

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object App extends App {
  implicit val actorSystem = ActorSystem("ActorSystem")

  val filePath: Path = Paths.get("data.csv")
  val siteUrl: Uri = "https://flatfy.lun.ua/uk/"

  val agregator = Agregator(filePath, siteUrl)

  val res = agregator.run()



  val awaitRes = Await.result(res, Duration.Inf)
  println(awaitRes)


}

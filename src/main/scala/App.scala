import java.nio.file.{Path, Paths}
import java.util.concurrent.TimeUnit

import akka.{Done, NotUsed}
import akka.actor.{ActorSystem, CoordinatedShutdown}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.Uri
import akka.stream.scaladsl.Source
import controller.FlatfyController
import models.FlatfyModel
import services.ParsingCsvService.parsingStreetsCsv
import services.Aggregator
import spray.json.JsValue

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object App extends App {
  implicit val actorSystem = ActorSystem("ActorSystem")

  import actorSystem.dispatcher

  val binding: Future[Http.ServerBinding] = Http().bindAndHandle(WebRoutes.route, interface = "localhost", port = 8080)

  actorSystem.registerOnTermination {
    binding.flatMap(_.unbind())
  }

}

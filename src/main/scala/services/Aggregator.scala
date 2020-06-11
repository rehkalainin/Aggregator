package services

import java.nio.file.Path

import akka.actor.ActorSystem
import akka.http.scaladsl.model.Uri
import akka.stream.scaladsl.{Sink, Source}
import models.FlatfyModel
import spray.json.{DefaultJsonProtocol, JsValue, JsonWriter}

import scala.concurrent.Future

case class Aggregator(filePath: Path, siteUrl: Uri) extends DefaultJsonProtocol {

  implicit val actorSystem = ActorSystem("Agregator")

  import actorSystem.dispatcher


  def validationData(seq: Seq[List[FlatfyModel]]) = {
    val list = seq.toList.flatten
    list.filterNot(model => model.priceSqm == "none" | model.priceSqm < "100")
  }

  def toJson(list: List[FlatfyModel])(
    implicit jsWriter: JsonWriter[List[FlatfyModel]]): JsValue = jsWriter.write(list)

  def findAll() = {


    val streetsFromCsv: Future[Set[String]] = ParsingCsvService.parsingStreetsCsv(filePath)

    Source
      .future(streetsFromCsv)
      .mapConcat(_.toSet)
      .map { street =>
        ScrapingService.scrapeListings(street) // List[Element]
          .map(element => ScrapingService.scrapeData(element))
      }
      .runWith(Sink.seq)
      .map(validationData)
      .map(_.sortWith((li1, li2) => li1.priceSqm.toInt < li2.priceSqm.toInt))
      //.map(toJson)
  }
}
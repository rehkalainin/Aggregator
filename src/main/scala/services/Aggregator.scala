package services

import java.nio.file.Path

import akka.http.scaladsl.model.Uri
import akka.stream.scaladsl.{Sink, Source}
import io.circe.generic.auto._
import io.circe.syntax._
import models.FlatfyModel
import modules.FlatfyModul._

import scala.concurrent.Future

case class Aggregator(filePath: Path, siteUrl: Uri){

  import actorSystem.dispatcher

  val csvService = CsvService()
  val scrapingService = ScrapingService

  def findAll() = {

    def preparationData(seq: Seq[List[FlatfyModel]]) = {
      val list = seq.toList.flatten
      list.filterNot(model => model.priceSqm == "none")
          .filterNot(model => model.priceSqm.toInt < 100)
    }

    val streetsFromCsv: Future[Set[String]] = csvService.parsingStreetsCsv(filePath)

    Source
      .future(streetsFromCsv)
      .mapConcat(_.toSet)
      .map { street =>
        scrapingService.scrapeListings(street) // List[Element]
          .map(element => scrapingService.scrapeData(element))
      }
      .runWith(Sink.seq)
      .map(preparationData)
      .map(_.sortWith((li1, li2) => li1.priceSqm.toInt < li2.priceSqm.toInt))
      .map(_.take(10))
      .map(_.asJson)
  }

}

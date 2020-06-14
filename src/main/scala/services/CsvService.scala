package services

import java.nio.file.Path

import akka.actor.ActorSystem
import akka.stream.alpakka.csv.scaladsl.{CsvParsing, CsvToMap}
import akka.stream.scaladsl.{FileIO, Sink}
import models.CsvDataModel
import modules.FlatfyModul._

import scala.concurrent.Future

case class CsvService()(implicit actor: ActorSystem) {

  import actorSystem.dispatcher


  def cleanseCsvData(csvData: Map[String, String]): Map[String, String] = {
    csvData
      .filter { case (key, _) => key == "Область" | key == "Адреса" | key == "Дата" }
  }

  def createCsvDataModels(data: Map[String, String]): CsvDataModel = {
    val street: String = data.getOrElse("Адреса", " ")
    val region = data.getOrElse("Область", " ")
    val model = CsvDataModel(street, region)
    model
  }

  def scrapingStreets(seq: Seq[CsvDataModel]): Set[String] = {
    val set = seq.toSet
    set.map { model =>
      val fullStreet = model.street.split(",").head
      fullStreet.split(" ").last
    }.filterNot(name => name.contains("."))
  }


  def parsingStreetsCsv(filePath: Path): Future[Set[String]] = {
    try {
      FileIO.fromPath(filePath)
        .via(CsvParsing.lineScanner()) //: List[ByteString]
        .via(CsvToMap.toMapAsStrings()) //: Map[String, String]
        .map(cleanseCsvData) // Map[String,String]
        .map(createCsvDataModels) //CsvDataModel
        .filter { model => model.rigion == "Одеська" }
        .runWith(Sink.seq) //Seq[CsvDataModel]
        .map(scrapingStreets)
    } catch {
      case ex: Exception => {
        actorSystem.log.error(s"Problem with scraping data from csv: $ex")
        Future(Set())
      }
    }

  }
}

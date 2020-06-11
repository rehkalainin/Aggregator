package modules

import java.nio.file.{Path, Paths}

import akka.http.scaladsl.model.Uri
import controller.FlatfyController
import services.{Aggregator, AggregatorServiceImpl}

object FlatfyModul {

  val filePath: Path = Paths.get("data.csv")
  val siteUrl: Uri = "https://flatfy.lun.ua/uk/"

  val agregator = Aggregator(filePath, siteUrl)
  lazy val flatfyService = new AggregatorServiceImpl(agregator)
  lazy val flatfyController = new FlatfyController(flatfyService)

}

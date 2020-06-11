package services

import java.nio.file.{Path, Paths}

import akka.http.scaladsl.model.Uri
import models.FlatfyModel

import scala.concurrent.Future

trait AggregatorService {
  def findAll(): Future[List[FlatfyModel]]
}

class AggregatorServiceImpl(agregator: Aggregator) extends AggregatorService {
  override def findAll(): Future[List[FlatfyModel]] = {

    agregator.findAll()

  }
}


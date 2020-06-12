package services

import io.circe.Json

import scala.concurrent.Future

trait AggregatorService {
  def findAll(): Future[Json]
}

class AggregatorServiceImpl(agregator: Aggregator) extends AggregatorService {

  override def findAll() = {

    agregator.findAll()


  }
}


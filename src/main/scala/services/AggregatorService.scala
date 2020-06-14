package services

import io.circe.Json
import io.circe.syntax._
import modules.FlatfyModul._

import scala.concurrent.Future


trait AggregatorService {
  def findAll(): Future[Json]
}

class AggregatorServiceImpl(agregator: AggregatorScheduler) extends AggregatorService {

  import actorSystem.dispatcher


  override def findAll() = {
    val aggregatedData = agregator.getAggregatedData()
    aggregatedData match {
      case Some(value) => Future(value)
      case None=> {
        actorSystem.log.error("Failed to update data, please wait new update")
        Future("Was problem on server side, please wait new update".asJson)
      }
    }
  }
}




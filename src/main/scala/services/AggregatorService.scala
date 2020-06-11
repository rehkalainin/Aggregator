package services

import java.util.concurrent.TimeUnit

import io.circe.generic.auto._
import io.circe.syntax._
import akka.actor.{ActorSystem, Cancellable}
import akka.stream.scaladsl.Source
import io.circe.Json
import models.FlatfyModel

import scala.concurrent.Future
import scala.concurrent.duration._

trait AggregatorService {
  def findAll(): Future[Json]
}

class AggregatorServiceImpl(agregator: Aggregator) extends AggregatorService {

  override def findAll() = {

    agregator.findAll()
//    val (ticks, futureList): (Cancellable, Future[List[FlatfyModel]]) =
//      Source
//        .tick(1.second, 7.second, agregator.findAll())
//
//    TimeUnit.SECONDS.sleep(59)
//    ticks.cancel()
//    futureList

  }
}


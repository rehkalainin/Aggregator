package services

import java.util.concurrent.atomic.AtomicReference

import akka.actor.ActorSystem
import io.circe.Json
import modules.FlatfyModul._

import scala.concurrent.duration._
import scala.util.{Failure, Success}

class AggregatorScheduler(aggregator: Aggregator)(implicit system: ActorSystem) {

  private val storedData = new AtomicReference(Option.empty[Json])

  import system.dispatcher

  def start(): Unit =
    system.scheduler.scheduleWithFixedDelay(Duration.Zero, 60.minute) { () =>
      aggregator.findAll().onComplete {
        case Failure(e) => actorSystem.log.error(s"Can't scrape data from site:  $e")
        case Success(newData) => {
          actorSystem.log.info("Update data in Store")
          storedData.set(Some(newData))
        }
      }
    }

  def getAggregatedData(): Option[Json] = storedData.get
}




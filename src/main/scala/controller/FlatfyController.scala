package controller

import akka.http.scaladsl.server.Route
import models.FlatfyModel
import services.AggregatorService

import scala.concurrent.Future

class FlatfyController(service:AggregatorService) extends ControllerBase {
  override def route: Route =
    apiV1 {
      pathPrefix("flatfy") {
        pathPrefix("list") {
          pathEndOrSingleSlash {
            get {
              onSuccess(service.findAll()) { list =>
                complete(list.mkString("\n"))
              }
            }
          }
        }
      }
    }
}

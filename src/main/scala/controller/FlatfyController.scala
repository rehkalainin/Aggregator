package controller

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Route
import services.AggregatorService
import modules.FlatfyModul._

class FlatfyController(service: AggregatorService) extends ControllerBase {

  import actorSystem.dispatcher

  override def route: Route =
    apiV1 {
      pathPrefix("flatfy") {
        pathPrefix("list") {
          pathEndOrSingleSlash {
            get {
              onSuccess(service.findAll()) { json =>
                complete(HttpEntity(ContentTypes.`application/json`, json.toString()))
              }
            }
          }
        }
      }
    }
}

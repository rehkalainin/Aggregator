package controller

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Route
import services.AggregatorService

class FlatfyController(service: AggregatorService) extends ControllerBase {
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

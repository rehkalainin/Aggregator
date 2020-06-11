import akka.http.scaladsl.server.{Route, RouteConcatenation}
import models.FlatfyModel
import modules.FlatfyModul

object WebRoutes {

  def route: Route = {
    RouteConcatenation.concat(
      FlatfyModul.flatfyController.route
    )
  }

}

package controller

import akka.http.scaladsl.server.{Directives, Route}

trait ControllerBase extends Directives  {
  def route: Route

  val apiV1 = pathPrefix("api" / "v1")

}

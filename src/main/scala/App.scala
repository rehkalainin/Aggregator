
import akka.http.scaladsl.Http
import modules.FlatfyModul._

import scala.concurrent.Future

object App extends App {

  import actorSystem.dispatcher

  aggregatorScheduler.start()

  val binding: Future[Http.ServerBinding] = Http().bindAndHandle(WebRoutes.route, interface = "localhost", port = 8080)


  actorSystem.registerOnTermination {
    binding.flatMap(_.unbind())
  }

}

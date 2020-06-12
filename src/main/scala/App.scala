
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import services.CsvService
import scala.concurrent.Future
import modules.FlatfyModul._

object App extends App {

  import actorSystem.dispatcher

  val binding: Future[Http.ServerBinding] = Http().bindAndHandle(WebRoutes.route, interface = "localhost", port = 8080)


  actorSystem.registerOnTermination {
    binding.flatMap(_.unbind())
  }

}

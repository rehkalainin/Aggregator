
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import scala.concurrent.Future

object App extends App {
  implicit val actorSystem = ActorSystem("ActorSystem")

  import actorSystem.dispatcher

  val binding: Future[Http.ServerBinding] = Http().bindAndHandle(WebRoutes.route, interface = "localhost", port = 8080)


  actorSystem.registerOnTermination {
    binding.flatMap(_.unbind())
  }

}

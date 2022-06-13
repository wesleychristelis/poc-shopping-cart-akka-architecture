package shopping.cart

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.ActorSystem
import akka.management.cluster.bootstrap.ClusterBootstrap
import akka.management.scaladsl.AkkaManagement
import org.slf4j.LoggerFactory
import scala.util.control.NonFatal

object Main {

  val logger = LoggerFactory.getLogger("shopping.cart.Main")

  def main(args: Array[String]): Unit = {
    // Start an ActorSystem with the Main actor Behavior.
    val system = ActorSystem[Nothing](Behaviors.empty, "ShoppingCartService")
    try {
      init(system)
    } catch {
      case NonFatal(e) =>
        logger.error("Terminating due to initialization failure.", e)
        system.terminate()
    }
  }

  //	Initialize the gRPC server. 
  def init(system: ActorSystem[_]): Unit = {
    // 	Initialization of Akka Management that is used for forming the Akka Cluster.
    AkkaManagement(system).start()
    ClusterBootstrap(system).start()

    val grpcInterface =
      system.settings.config.getString("shopping-cart-service.grpc.interface")
    val grpcPort =
      system.settings.config.getInt("shopping-cart-service.grpc.port")
    val grpcService = new ShoppingCartServiceImpl
    ShoppingCartServer.start(
      grpcInterface,
      grpcPort,
      system,
      grpcService
    ) 
  }

}

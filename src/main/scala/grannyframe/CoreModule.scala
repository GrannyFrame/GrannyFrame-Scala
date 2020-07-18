package grannyframe

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import pureconfig.ConfigSource
import pureconfig.generic.auto._

import scala.concurrent.ExecutionContextExecutor


trait CoreModule {
  lazy val config = ConfigSource.default.loadOrThrow[Config]
  lazy val actorSystem = ActorSystem("GrannyFrameSystem", ConfigFactory.load("akka.conf"))
  implicit def ex: ExecutionContextExecutor = actorSystem.dispatcher

  def bootstrap(): Unit = {}
  def shutdown(): Unit = {}

}

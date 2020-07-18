package grannyframe.telegram

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging
import grannyframe.persistence.PersistenceModule
import grannyframe.{Config, CoreModule}
import pureconfig.ConfigSource
import telegram.bot.GrannyFrameBot

import scala.concurrent.Await
import scala.concurrent.duration.Duration

trait TelegramModule extends CoreModule with LazyLogging {

  lazy val actorSystem = ActorSystem("StoerageBackend", ConfigFactory.load("akka.conf"))
  lazy val bot = new GrannyFrameBot(config.bot.token)

  abstract override def bootstrap(): Unit = {
    super.bootstrap()

    val eol = bot.run()

    actorSystem.registerOnTermination {
      bot.shutdown() // initiate shutdown
      // Wait for the bot end-of-life
      Await.result(eol, Duration.Inf)
    }
    logger.info(s"Telegram Module started with token: ${config.bot.token}")
  }

  abstract override def shutdown(): Unit = {
    Await.result(actorSystem.terminate(), Duration.Inf)
    logger.debug("Telegram Module Stopped")
    super.shutdown()
  }
}

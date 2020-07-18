package grannyframe.telegram

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging
import grannyframe.Config
import pureconfig.ConfigSource
import telegram.bot.GrannyFrameBot

import scala.concurrent.Await
import scala.concurrent.duration.Duration

import pureconfig.generic.auto._

object Core extends LazyLogging{
  def bootstrap(): Unit = {
    val config = ConfigSource.default.loadOrThrow[Config]
    val actorSystem = ActorSystem("StoerageBackend", ConfigFactory.load("akka.conf"))

    logger.info(s"GrannyFrame Started with token: ${config.bot.token}")

    val bot = new GrannyFrameBot(config.bot.token)
    val eol = bot.run()
    logger.info("Press [ENTER] to shutdown the bot, it may take a few seconds...")
    scala.io.StdIn.readLine()
    bot.shutdown() // initiate shutdown
    // Wait for the bot end-of-life
    Await.result(eol, Duration.Inf)
    logger.info(s"GrannyFrame Stopped!")
  }
}

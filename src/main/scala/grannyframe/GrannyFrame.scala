package grannyframe

import com.typesafe.scalalogging.LazyLogging
import grannyframe.persistence.PersistenceModule
import grannyframe.telegram.TelegramModule

object GrannyFrame extends LazyLogging {
  def main(args: Array[String]): Unit = {
    val module = new CoreModule with PersistenceModule with TelegramModule {}

    module.bootstrap()
    logger.info("Press [ENTER] to shutdown the bot, it may take a few seconds...")
    scala.io.StdIn.readLine()

    module.shutdown()

    logger.info(s"GrannyFrame Stopped!")

  }
}

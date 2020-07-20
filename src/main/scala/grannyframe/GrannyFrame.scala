package grannyframe

import com.typesafe.scalalogging.LazyLogging
import grannyframe.persistence.PersistenceModule
import grannyframe.telegram.TelegramModule
import grannyframe.ui.UiModule

object GrannyFrame extends LazyLogging {
  def main(args: Array[String]): Unit = {
    val module = new CoreModule with PersistenceModule with TelegramModule with UiModule {}

    module.bootstrap()

    module.shutdown()

    logger.info(s"GrannyFrame Stopped!")

  }
}

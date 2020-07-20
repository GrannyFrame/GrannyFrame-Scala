package grannyframe.ui

import com.typesafe.scalalogging.LazyLogging
import grannyframe.persistence.PersistenceModule
import grannyframe.ui.scalafx.MainUI
import javafx.application.Application

trait UiModule extends PersistenceModule with LazyLogging {

  abstract override def bootstrap(): Unit = {
    super.bootstrap()
    logger.debug("UI Module Started")
    Application.launch(classOf[MainUI])
  }

  abstract override def shutdown(): Unit = {
    logger.debug("UI Module Stopped")
    super.shutdown()
  }
}

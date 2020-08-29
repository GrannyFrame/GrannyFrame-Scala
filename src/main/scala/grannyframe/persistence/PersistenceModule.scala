package grannyframe.persistence

import com.typesafe.scalalogging.LazyLogging
import grannyframe.CoreModule
import grannyframe.ui.jfx.UIController

trait PersistenceModule extends CoreModule with LazyLogging {

  lazy val store = new DBStore(config)

  abstract override def bootstrap(): Unit = {
    super.bootstrap()
    logger.debug("Persistence Module Started")
  }

  abstract override def shutdown(): Unit = {
    logger.debug("Persistence Module Stopped")
    super.shutdown()
  }

}

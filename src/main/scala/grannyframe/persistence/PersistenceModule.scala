package grannyframe.persistence

import com.typesafe.scalalogging.LazyLogging
import grannyframe.CoreModule

trait PersistenceModule extends CoreModule with LazyLogging {

  abstract override def bootstrap(): Unit = {
    super.bootstrap()
    logger.debug("Persistence Module Started")
  }

  abstract override def shutdown(): Unit = {
    logger.debug("Persistence Module Stopped")
    super.shutdown()
  }

}

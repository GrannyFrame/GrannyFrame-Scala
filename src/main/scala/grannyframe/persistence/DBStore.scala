package grannyframe.persistence

import com.typesafe.scalalogging.LazyLogging
import grannyframe.ui.jfx.UIController

import scala.concurrent.{ExecutionContext, Future}

class DBStore(val uiController: UIController)(implicit val ex: ExecutionContext) extends LazyLogging {
  def saveImage(image: ImageEntity): Future[Unit] = {
    logger.info("saving image: {}", image)
    //TODO save to db
    //TODO through DB, notify UI

    Future.successful(())
  }
}

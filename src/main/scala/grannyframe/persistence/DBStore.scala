package grannyframe.persistence

import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.{ExecutionContext, Future}

class DBStore(implicit ex: ExecutionContext) extends LazyLogging {
  def saveImage(image: ImageEntity): Future[Unit] = {
    logger.info("saving image: {}", image)
    //TODO save to db
    //TODO through DB, notify UI
    Future.successful(())
  }
}

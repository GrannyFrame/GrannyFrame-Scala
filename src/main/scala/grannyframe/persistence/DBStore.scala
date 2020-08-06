package grannyframe.persistence

import com.typesafe.scalalogging.LazyLogging
import grannyframe.ui.jfx.UIController
import javafx.application.Platform

import scala.concurrent.{ExecutionContext, Future}

class DBStore()(implicit val ex: ExecutionContext) extends LazyLogging {
  var ui: UIController = null

  def saveImage(image: ImageEntity): Future[Unit] = {
    logger.info("saving image: {}", image)
    //TODO save to db
    //TODO through DB, notify UI
    println(ui)
    Platform.runLater(() => ui.showImage(image))

    Future.successful(())
  }

  def setUI(ui: UIController): Unit = {
    this.ui = ui;
  }
}

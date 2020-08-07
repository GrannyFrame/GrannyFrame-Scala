package grannyframe.persistence

import com.typesafe.scalalogging.LazyLogging
import grannyframe.ui.jfx.UIController
import javafx.application.Platform
import org.mongodb.scala.{FindObservable, MongoClient, MongoCollection, MongoDatabase}
import org.mongodb.scala.model.Sorts._

import scala.concurrent.{ExecutionContext, Future}

class DBStore(val dbConStr: String, val db: String)(implicit val ex: ExecutionContext) extends LazyLogging {
  var ui: UIController = null

  val mongoClient: MongoClient = MongoClient(dbConStr)
  val database: MongoDatabase = mongoClient.getDatabase(db)
  val collection: MongoCollection[ImageEntity] = database.getCollection("Pictures")

  def saveImage(image: ImageEntity): Future[Unit] = {
    logger.info("saving image: {}", image)

    // Save to db
    collection.insertOne(image)

    //Notify UI
    Platform.runLater(() => ui.showImage(image))

    Future.successful(())
  }

  def getImages(n: Int): FindObservable[ImageEntity] = {
    collection.find[ImageEntity]().limit(n)
  }

  def setUI(ui: UIController): Unit = {
    this.ui = ui;
  }
}

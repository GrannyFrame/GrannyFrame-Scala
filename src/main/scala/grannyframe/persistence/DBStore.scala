package grannyframe.persistence

import com.typesafe.scalalogging.LazyLogging
import grannyframe.Config
import grannyframe.ui.jfx.UIController
import javafx.application.Platform
import org.mongodb.scala.{Document, FindObservable, MongoClient, MongoCollection, MongoDatabase}
import org.mongodb.scala.model.Sorts._
import org.mongodb.scala._
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.MongoClient.DEFAULT_CODEC_REGISTRY
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.CodecRegistry

import scala.concurrent.{ExecutionContext, Future}

class DBStore(val config: Config)(implicit val ex: ExecutionContext) extends LazyLogging {
  var ui: UIController = _

  val codecRegistry: CodecRegistry = fromRegistries(fromProviders(classOf[ImageEntity]), DEFAULT_CODEC_REGISTRY )

  val mongoClient: MongoClient = MongoClient(config.database.connectionString)
  val database: MongoDatabase = mongoClient.getDatabase(config.database.database).withCodecRegistry(codecRegistry)
  val collection: MongoCollection[ImageEntity] = database.getCollection("Pictures")

  def saveImage(image: ImageEntity): Future[Unit] = {
    logger.info("saving image: {}", image)

    // Save to db
    for {
      _ <- collection.insertOne(image).toFuture()
      imgs <- getImages()
    } yield {
      Platform.runLater { () =>
        ui.showNewImage(imgs)
      }
    }
  }

  def getImages(n: Int = config.frontend.imageCount): Future[List[ImageEntity]] = {
    collection.find[ImageEntity]()
      .sort(descending("createdAt"))
      .limit(n)
      .toFuture()
      .map(_.toList)
  }

  def showInitialImages():  Future[Unit] = {
    getImages().map { imgs =>
      Platform.runLater { () =>
        ui.showNewImage(imgs)
      }
    }
  }

  def setUI(ui: UIController): Unit = {
    this.ui = ui
  }
}

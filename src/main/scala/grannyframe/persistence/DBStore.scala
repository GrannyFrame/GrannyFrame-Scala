package grannyframe.persistence

import com.typesafe.scalalogging.LazyLogging
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

class DBStore(val dbConStr: String, val db: String)(implicit val ex: ExecutionContext) extends LazyLogging {
  var ui: UIController = _

  val codecRegistry: CodecRegistry = fromRegistries(fromProviders(classOf[ImageEntity]), DEFAULT_CODEC_REGISTRY )

  val mongoClient: MongoClient = MongoClient(dbConStr)
  val database: MongoDatabase = mongoClient.getDatabase(db).withCodecRegistry(codecRegistry)
  val collection: MongoCollection[ImageEntity] = database.getCollection("Pictures")

  def saveImage(image: ImageEntity): Future[Unit] = {
    logger.info("saving image: {}", image)

    // Save to db
    collection.insertOne(image).subscribe(new Observer[Completed] {
      override def onNext(result: Completed): Unit = println(s"onNext: $result")
      override def onError(e: Throwable): Unit = println(s"onError: $e")
      override def onComplete(): Unit = println("onComplete")
    })

    //Notify UI
    Platform.runLater(() => ui.showImage(image))

    Future.successful(())
  }

  def getImages(n: Int): FindObservable[ImageEntity] = {
    collection.find[ImageEntity]().limit(n)
  }

  def setUI(ui: UIController): Unit = {
    this.ui = ui
  }
}

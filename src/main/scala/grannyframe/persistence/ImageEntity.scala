package grannyframe.persistence

import java.time.Instant

import akka.http.scaladsl.model.MediaTypes
import org.mongodb.scala.bson.ObjectId

case class ImageEntity(_id: ObjectId,
                       sender: String,
                       caption: Option[String],
                       bytes: Array[Byte],
                       mediaType: String = MediaTypes.`image/jpeg`.value,
                       createdAt: Instant = Instant.now)

package grannyframe.persistence

import java.time.Instant

import akka.http.scaladsl.model.MediaTypes

case class ImageEntity(sender: String,
                       caption: Option[String],
                       bytes: Array[Byte],
                       mediaType: String = MediaTypes.`image/jpeg`.value,
                       createdAt: Instant = Instant.now)

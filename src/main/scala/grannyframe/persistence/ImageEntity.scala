package grannyframe.persistence

import java.time.Instant

case class ImageEntity(sender: String,
                       caption: Option[String],
                       bytes: Array[Byte],
                       mediaType: String,
                       createdAt: Instant = Instant.now)

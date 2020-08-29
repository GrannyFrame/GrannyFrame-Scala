package telegram.bot

import java.time.Instant

import akka.http.scaladsl.model.MediaTypes
import cats.instances.future._
import cats.syntax.functor._
import com.bot4s.telegram.api.RequestHandler
import com.bot4s.telegram.api.declarative.Commands
import com.bot4s.telegram.clients.{FutureSttpClient}
import com.bot4s.telegram.future.{Polling, TelegramBot}
import com.bot4s.telegram.methods.GetFile
import com.softwaremill.sttp.SttpBackend
import com.softwaremill.sttp.okhttp.OkHttpFutureBackend
import grannyframe.persistence.{DBStore, ImageEntity}
import org.bson.types.ObjectId
import slogging.{LogLevel, LoggerConfig, PrintLoggerFactory}

import scala.util.Try
import scala.concurrent.{ExecutionContext, Future}

class GrannyFrameBot(val token: String, store: DBStore)(override implicit val executionContext: ExecutionContext) extends TelegramBot
  with Polling
  with Commands[Future] {

  LoggerConfig.factory = PrintLoggerFactory()
  LoggerConfig.level = LogLevel.TRACE

  implicit val backend: SttpBackend[Future, Nothing] = OkHttpFutureBackend()
  override val client: RequestHandler[Future] = new FutureSttpClient(token)

  onCommand("start") { implicit msg =>
    logger.debug(s"Recieved Message from: ${msg.from.get}")
    reply("Welcome to the GrannyFrame Bot").void
  }

  onMessage { implicit msg =>
    logger.debug("received msg: {}", msg)

    msg.photo.map { photos =>
      val latestPhoto = photos.last
      logger.info("received image: {}", latestPhoto.fileId)
      request(GetFile(latestPhoto.fileId)).flatMap { file =>
        file.filePath match {
          case Some(filePath) =>
            val url = s"https://api.telegram.org/file/bot$token/$filePath"
            val bytesResponse = Future { scalaj.http.Http(url).asBytes }
            bytesResponse
              .filter(_.isSuccess)
              .map(_.body)
              .flatMap { bytes =>
                logger.info("received {} bytes for fileId: {}", bytes.length, latestPhoto.fileId)
                store.saveImage(ImageEntity(new ObjectId(), msg.from.get.firstName, msg.caption, bytes, MediaTypes.`image/jpeg`.value, Instant.ofEpochSecond(msg.date)))
              }
              .flatMap(_ => reply("Bild erhalten.").void)
              .recoverWith {
                case ex =>
                  logger.error("Could not save Image:", ex)
                  reply("Bildverarbeitung fehlgeschlagen").void
              }
          case None =>
            logger.error("Could not retrieve filepath for id: {}", latestPhoto.fileId)
            reply("Fehler beim empfangen des Bildes").void
        }
      }

    }.getOrElse {
      reply("This bot can only receive Pictures").void
    }
  }

  // Int(n) extractor
  object Int {
    def unapply(s: String): Option[Int] = Try(s.toInt).toOption
  }

}
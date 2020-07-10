package telegram

import cats.instances.future._
import cats.syntax.functor._
import com.bot4s.telegram.api.RequestHandler
import com.bot4s.telegram.api.declarative.Commands
import com.bot4s.telegram.clients.{FutureSttpClient, ScalajHttpClient}
import com.bot4s.telegram.future.{Polling, TelegramBot}
import com.bot4s.telegram.methods.GetFile
import com.bot4s.telegram.models.Message
import com.softwaremill.sttp.okhttp.OkHttpFutureBackend
import slogging.{LogLevel, LoggerConfig, PrintLoggerFactory}

import scala.util.{Success, Try}
import scala.concurrent.Future

class GrannyFrameBot(val token: String) extends TelegramBot
  with Polling
  with Commands[Future] {

  LoggerConfig.factory = PrintLoggerFactory()
  LoggerConfig.level = LogLevel.TRACE

  implicit val backend = OkHttpFutureBackend()
  override val client: RequestHandler[Future] = new FutureSttpClient(token)

  onCommand("start") { implicit msg =>
    println(s"Recieved Message from: ${msg.from.get}")
    reply("Welcome to the GrannyFrame Bot").void
  }

  onMessage { implicit msg =>
    if (msg.photo.isEmpty) {
      reply("This bot can only recieve Pictures")
    }
    using(_.photo) { photo =>
      request(GetFile(photo.last.fileId)).andThen({
        case Success(file) =>
          file.filePath match {

            case Some(filePath) =>
              val url = s"https://api.telegram.org/file/bot${token}/${filePath}"

              for {
                res <- Future {
                  scalaj.http.Http(url).asBytes
                }
                if res.isSuccess
                bytes = res.body
                _ <- reply(s"File with ${bytes.size} bytes received.").void
              } yield ()
            case None =>
              reply("No file_path was returned").void
          }
      }).void
    }

  }

  // Int(n) extractor
  object Int {
    def unapply(s: String): Option[Int] = Try(s.toInt).toOption
  }

}
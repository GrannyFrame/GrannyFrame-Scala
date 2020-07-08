import telegram.GrannyFrameBot

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object GrannyFrame {
  def main(args: Array[String]): Unit = {
    val token: String = sys.env.getOrElse("TBOT_TOKEN", "1006864390:AAEOS88x9Ljw7Z1ypSbtTZEW0KlzKPpf7Ak")
    println(s"GrannyFrame Started with token: $token")

    val bot = new GrannyFrameBot(token)
    val eol = bot.run()
    println("Press [ENTER] to shutdown the bot, it may take a few seconds...")
    scala.io.StdIn.readLine()
    bot.shutdown() // initiate shutdown
    // Wait for the bot end-of-life
    Await.result(eol, Duration.Inf)
    println(s"GrannyFrame Stopped!")
  }
}

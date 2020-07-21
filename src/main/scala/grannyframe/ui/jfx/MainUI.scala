package grannyframe.ui.jfx

import java.nio.file.{Files, Paths}
import java.time.Instant

import akka.http.scaladsl.model.headers.CacheDirectives.public
import com.typesafe.scalalogging.LazyLogging
import grannyframe.persistence.ImageEntity
import javafx.application.Application
import javafx.event.{ActionEvent, EventHandler}
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.StackPane
import javafx.stage.Stage

class MainUI extends Application with LazyLogging{
  override def start(stage: Stage): Unit = {
    stage.setTitle("GrannyFrame")
    stage.setFullScreen(true)

    //Test
    val imageWeb = "/home/felix/Desktop/20190801_184527_HDR.jpg"
    val testimage = ImageEntity("Clara",
      Option[String]("München, here we are! \uD83D\uDE00"),
      Files.newInputStream(Paths.get(imageWeb)).readAllBytes()
    )
    //EndTest

    val viewer = new ImageViewer();

    viewer.displayImage(testimage)//Test

    stage.setScene(new Scene(viewer, 800, 600))

    stage.show()
  }
}

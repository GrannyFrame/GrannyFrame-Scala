package grannyframe

import java.nio.file.{Files, Paths}

import com.typesafe.scalalogging.LazyLogging
import grannyframe.persistence.{ImageEntity, PersistenceModule}
import grannyframe.telegram.TelegramModule
import grannyframe.ui.jfx.UIController
import grannyframe.ui.jfx.views.ImageViewer
import javafx.application.{Application, Platform}
import javafx.scene.Scene
import javafx.stage.Stage

class GrannyFrame extends Application with LazyLogging {
  override def start(stage: Stage): Unit = {
    logger.info("Starting FrannyFrame...")
    logger.info("Starting Modules")
    implicit val uiCtrl: UIController = new UIController();
    val modules = new CoreModule with PersistenceModule with TelegramModule {}
    modules.bootstrap()

    stage.setOnCloseRequest(evt => {
      Platform.exit()
      modules.shutdown()
      logger.info("Granny Frame Stopped")
      System.exit(0)
    })

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

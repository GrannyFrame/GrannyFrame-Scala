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

    val modules = new CoreModule with PersistenceModule with TelegramModule {}

    val uiCtrl = new UIController(stage, modules.actorSystem, modules.config)

    modules.bootstrap()

    modules.store.setUI(uiCtrl)

    stage.setOnCloseRequest(evt => {
      Platform.runLater(() => uiCtrl.showSplash("Bilderrahmen wird beendet!\nBitte habe noch etwas geduld!"))
      Thread.sleep(200)
      Platform.exit()
      modules.shutdown()
      logger.info("Granny Frame Stopped")
      System.exit(0)
    })

    uiCtrl.showSplash("Der Bilderrahmen startet gerade...\nBitte warte noch ein wenig...")

    logger.info("Grannyframe started!")
  }
}

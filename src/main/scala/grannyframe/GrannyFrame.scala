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

import scala.util.Failure

class GrannyFrame extends Application with LazyLogging {
  override def start(stage: Stage): Unit = {
    logger.info("Starting FrannyFrame...")
    logger.info("Starting Modules")

    val modules = new CoreModule with PersistenceModule with TelegramModule {}

    val uiCtrl = new UIController(stage, modules.actorSystem, modules.config)

    modules.bootstrap()

    modules.store.setUI(uiCtrl)

    modules.store.showInitialImages().onComplete {
      case Failure(ex) =>
        logger.error("Could not show Initial Images from DB", ex)
      case _ =>
    }(modules.ex)

    stage.setOnCloseRequest(evt => {
      Platform.runLater(() => uiCtrl.showSplash("Bilderrahmen wird beendet!\nBitte habe noch etwas geduld!"))
      Thread.sleep(200)
      Platform.exit()
      modules.shutdown()
      logger.info("Granny Frame Stopped")
      System.exit(0)
    })

    uiCtrl.showSplash("""Der Bilderrahmen ist gestartet!
                              |Leider sind noch keine Bilder vorhanden.
                              |Wartet bis ihr eier erstes Bild erhaltet...""".stripMargin)

    logger.info("Grannyframe started!")
  }
}

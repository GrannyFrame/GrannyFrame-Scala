package grannyframe.ui.jfx

import java.nio.file.{Files, Paths}

import grannyframe.persistence.ImageEntity
import grannyframe.ui.jfx.views.{ImageViewer, LoadingViewer}
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.stage.Stage

class UIController(val stage: Stage) {
  stage.setTitle("GrannyFrame")
  val frame = new StackPane()
  val scene = new Scene(frame)
  stage.setScene(scene)
  stage.setFullScreen(true)
  stage.show()

  val viewer = new ImageViewer();

  def showSplash(msg: String): Unit ={
    val splash = new LoadingViewer(msg)
    frame.getChildren.clear()
    frame.getChildren.add(splash)
  }

  def initImage(): Unit ={
    frame.getChildren.clear()
    frame.getChildren.add(viewer)
  }

  def showImage(imageEntity: ImageEntity): Unit = {
    if(!frame.getChildren.get(0).isInstanceOf[ImageViewer]) initImage()
    viewer.displayImage(imageEntity)
  }
}

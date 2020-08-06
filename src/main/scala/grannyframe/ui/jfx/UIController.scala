package grannyframe.ui.jfx

import grannyframe.persistence.ImageEntity
import grannyframe.ui.jfx.views.{ImageViewer, LoadingViewer}
import javafx.scene.Scene
import javafx.scene.layout.{AnchorPane, StackPane}
import javafx.stage.Stage

class UIController(val stage: Stage) {
  stage.setTitle("GrannyFrame")
  val frame = new AnchorPane()
  val scene = new Scene(frame, 800, 600)
  stage.setScene(scene)
  stage.setFullScreen(true)
  stage.show()

  val viewer = new ImageViewer()

  def showSplash(msg: String): Unit ={
    val splash = new LoadingViewer(msg)
    AnchorPane.setTopAnchor(splash, 0f)
    AnchorPane.setRightAnchor(splash, 0f)
    AnchorPane.setBottomAnchor(splash, 0f)
    AnchorPane.setLeftAnchor(splash, 0f)
    frame.getChildren.clear()
    frame.getChildren.add(splash)
  }

  def initImage(): Unit = {
    frame.getChildren.clear()
    AnchorPane.setTopAnchor(viewer, 0f)
    AnchorPane.setRightAnchor(viewer, 0f)
    AnchorPane.setBottomAnchor(viewer, 0f)
    AnchorPane.setLeftAnchor(viewer, 0f)
    frame.getChildren.add(viewer)
  }

  def showImage(imageEntity: ImageEntity): Unit = {
    if(!frame.getChildren.get(0).isInstanceOf[ImageViewer]) initImage()
    viewer.displayImage(imageEntity)
  }
}

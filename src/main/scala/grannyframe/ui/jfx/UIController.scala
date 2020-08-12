package grannyframe.ui.jfx

import grannyframe.persistence.ImageEntity
import grannyframe.ui.jfx.views.{ImageViewer, LoadingViewer}
import javafx.scene.{Node, Scene}
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
    setAnchorConst(splash)
    frame.getChildren.clear()
    frame.getChildren.add(splash)
  }

  def initImage(): Unit = {
    frame.getChildren.clear()
    setAnchorConst(viewer)
    frame.getChildren.add(viewer)
  }

  def showImage(imageEntity: ImageEntity): Unit = {
    if(!frame.getChildren.get(0).isInstanceOf[ImageViewer]) initImage()
    viewer.displayImage(imageEntity)
  }

  def showSlideshow(images: Seq[ImageEntity]): Unit = {

  }

  private def setAnchorConst(node: Node): Unit ={
    AnchorPane.setTopAnchor(node, 0f)
    AnchorPane.setRightAnchor(node, 0f)
    AnchorPane.setBottomAnchor(node, 0f)
    AnchorPane.setLeftAnchor(node, 0f)
  }
}

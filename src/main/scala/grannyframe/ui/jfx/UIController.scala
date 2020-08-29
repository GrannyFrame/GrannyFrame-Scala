package grannyframe.ui.jfx

import akka.actor.{ActorSystem, Props}
import grannyframe.Config
import grannyframe.persistence.ImageEntity
import grannyframe.ui.jfx.views.{ImageViewer, LoadingViewer}
import javafx.application.Platform
import javafx.scene.{Node, Scene}
import javafx.scene.layout.AnchorPane
import javafx.stage.Stage

class UIController(stage: Stage, system: ActorSystem, config: Config) {
  stage.setTitle("GrannyFrame")
  val frame = new AnchorPane()
  val scene = new Scene(frame, 800, 600)
  stage.setScene(scene)
  stage.setFullScreen(true)
  stage.show()

  val viewer = new ImageViewer()

  val slideshowActor = system.actorOf(Props(classOf[SlideshowActor], config, this), "SlideshowActor")

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

  def showNewImage(imgs: List[ImageEntity]): Unit = {
    slideshowActor! SlideshowActor.RescheduleSlideshow(imgs)
  }

  def showImage(imageEntity: ImageEntity): Unit = {
    Platform.runLater(() => {
      if(!frame.getChildren.get(0).isInstanceOf[ImageViewer]) initImage()
      viewer.displayImage(imageEntity)
    })
  }

  private def setAnchorConst(node: Node): Unit ={
    AnchorPane.setTopAnchor(node, 0f)
    AnchorPane.setRightAnchor(node, 0f)
    AnchorPane.setBottomAnchor(node, 0f)
    AnchorPane.setLeftAnchor(node, 0f)
  }
}

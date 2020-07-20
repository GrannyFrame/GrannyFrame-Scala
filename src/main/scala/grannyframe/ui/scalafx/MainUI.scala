package grannyframe.ui.scalafx

import akka.http.scaladsl.model.headers.CacheDirectives.public
import com.typesafe.scalalogging.LazyLogging
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

    val btn = new Button();
    btn.setText("Say 'Hello World'");
    btn.setOnAction(action => {
      logger.info("Hello World")
    })
    val root = new StackPane();
    root.getChildren().add(btn);
    stage.setScene(new Scene(root, 300, 250));

    stage.show()
  }
}

package grannyframe.ui.jfx.views

import javafx.geometry.Insets
import javafx.scene.layout.{Background, BackgroundFill, CornerRadii, StackPane}
import javafx.scene.paint.Color
import javafx.scene.text.{Font, Text, TextAlignment}

class LoadingViewer(val msg: String) extends StackPane{
  val splashText = new Text(msg)
  initComponents()

  private def initComponents(): Unit = {
    this.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(0.0), Insets.EMPTY)))
    splashText.setFont(Font.font(50))
    splashText.setFill(Color.WHITE)
    splashText.setTextAlignment(TextAlignment.CENTER)
    this.getChildren.add(splashText)
  }
}

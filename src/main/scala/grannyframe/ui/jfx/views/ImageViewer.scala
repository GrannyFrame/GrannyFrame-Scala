package grannyframe.ui.jfx.views

import java.io.ByteArrayInputStream
import java.time.ZoneId
import java.time.format.{DateTimeFormatter, FormatStyle}
import java.util.Locale

import better.files.Resource
import grannyframe.persistence.ImageEntity
import javafx.geometry.{Insets, Pos}
import javafx.scene.image.{Image, ImageView}
import javafx.scene.layout._
import javafx.scene.paint.Color
import javafx.scene.text.{Font, Text}

import scala.collection.JavaConverters._


class ImageViewer extends StackPane {
  val img = new ImageView()

  val caption = new Text()
  val captionBox = new VBox(caption)
  val captionLayer = new AnchorPane(captionBox)

  initComponents()

  private def initComponents(): Unit = {
    this.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(0.0), Insets.EMPTY)))

    img.fitWidthProperty.bind(this.widthProperty)
    img.fitHeightProperty().bind(this.heightProperty)
    img.setPreserveRatio(true)

    val emoji = Font.loadFont(Resource.getAsStream("OpenSansEmoji.ttf"), 18)
    caption.setFill(Color.WHITE)
    caption.setFont(emoji)

    captionBox.setAlignment(Pos.CENTER)
    this.heightProperty().addListener((arg0, arg1, arg2) => captionBox.setPrefHeight(arg2.doubleValue()*0.05))
    AnchorPane.setRightAnchor(captionBox, 0.0)
    AnchorPane.setBottomAnchor(captionBox, 0.0)
    AnchorPane.setLeftAnchor(captionBox, 0.0)
    captionBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5)")
    getChildren.addAll(Seq(img, captionLayer).asJava)
  }


  def displayImage(image: ImageEntity): Unit = {
    val dateFormatter = DateTimeFormatter.ofLocalizedDate( FormatStyle.SHORT )
        .withLocale( Locale.GERMAN )
        .withZone( ZoneId.systemDefault() );
    val timeFormatter = DateTimeFormatter.ofLocalizedTime( FormatStyle.SHORT )
      .withLocale( Locale.GERMAN )
      .withZone( ZoneId.systemDefault() );

    val date = dateFormatter.format(image.createdAt)
    val time = timeFormatter.format(image.createdAt)

    //TODO: Support Emoji! https://github.com/Madeorsk/EmojisFX
    caption.setText(s"${image.sender} am $date um $time ${image.caption.map(s => s":\n$s").getOrElse("")}")

    val img = new Image(new ByteArrayInputStream(image.bytes))
    this.img.setImage(img);
  }

}

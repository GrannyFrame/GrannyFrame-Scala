package grannyframe.ui.jfx

import akka.actor.{Actor, ActorLogging, Cancellable}
import grannyframe.Config
import grannyframe.persistence.ImageEntity
import grannyframe.ui.jfx.SlideshowActor.{RescheduleSlideshow, TickSlideshow}

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.DurationInt

class SlideshowActor(config: Config, uiCtrl: UIController) extends Actor with ActorLogging{
  implicit val ctx: ExecutionContext = this.context.system.dispatcher

  var cancellable = new Cancellable {
    override def cancel(): Boolean = true
    override def isCancelled: Boolean = true
  }

  var imgs: List[ImageEntity] = List.empty

  override def receive: Receive = {
    case RescheduleSlideshow(imgs) =>
      log.info("Received new Image List with {} elements", imgs.length)
      cancellable.cancel()
      this.imgs = imgs
      uiCtrl.showImage(imgs.head)
      val nextIdx = if(imgs.length > 1) 1 else 0
      log.debug("Rescheduling with NextIdx: {}", nextIdx)
      this.cancellable = this.context.system.scheduler.scheduleOnce(
        config.frontend.newImageTime seconds,
        self,
        TickSlideshow(nextIdx)
      )

    case TickSlideshow(idx) =>
      log.debug("Ticking slideshow to {}", idx)
      uiCtrl.showImage(imgs(idx))
      val nextIdx = if(idx < imgs.length - 1) idx + 1 else 0
      log.debug("Ticking slideshow with NextIdx: {}", nextIdx)
      this.cancellable = this.context.system.scheduler.scheduleOnce(
        config.frontend.imageTime seconds,
        self,
        TickSlideshow(nextIdx)
      )
  }
}

object SlideshowActor {
  case class RescheduleSlideshow(imgs: List[ImageEntity])
  case class TickSlideshow(idx: Int)
}

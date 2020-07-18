package grannyframe

import pureconfig.ConfigSource
import pureconfig.generic.auto._


trait CoreModule {
  lazy val config = ConfigSource.default.loadOrThrow[Config]

  def bootstrap(): Unit = {}
  def shutdown(): Unit = {}

}

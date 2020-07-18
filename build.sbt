name := "GrannyFrameScala"

version := "0.1"

scalaVersion := "2.12.11"

lazy val akkaVersion = "2.5.23"

libraryDependencies ++= List(
  // Core with minimal dependencies, enough to spawn your first telegram.bot.
  "com.bot4s" %% "telegram-core" % "4.4.0-RC2",

  // Extra goodies: Webhooks, support for games, bindings for actors.
  "com.bot4s" %% "telegram-akka" % "4.4.0-RC2",

  "com.softwaremill.sttp.client" %% "akka-http-backend" % "2.2.0",

  "ch.qos.logback"    % "logback-classic"           % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging"   % "3.9.+",
  "com.typesafe.akka" %% "akka-slf4j"               % akkaVersion,

  "com.github.pureconfig" %% "pureconfig" % "0.12.+",
)
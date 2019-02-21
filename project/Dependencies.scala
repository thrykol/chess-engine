import sbt._

object Dependencies {

  lazy val common = Seq(
    "org.scala-lang" % "scala-reflect" % "2.12.8",

    "com.typesafe.akka" %% "akka-actor" % "2.5.19",

    "com.typesafe.akka" %% "akka-slf4j" % "2.5.21",

    "ch.qos.logback" % "logback-classic" % "1.2.3",

    "com.typesafe.akka" %% "akka-testkit" % "2.5.21" % Test,

    "org.scalatest" %% "scalatest" % "3.0.5" % Test
  )
}

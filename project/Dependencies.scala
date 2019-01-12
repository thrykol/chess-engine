import sbt._

object Dependencies {

  lazy val common = Seq(
    "org.scala-lang" % "scala-reflect" % "2.12.8",

    "com.typesafe.akka" %% "akka-actor" % "2.5.19",

    "org.scalatest" %% "scalatest" % "3.0.5" % Test
  )
}

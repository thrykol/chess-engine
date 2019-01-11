import sbt._

object Dependencies {

  lazy val akka = Seq(
    "com.typesafe.akka" %% "akka-actor" % "2.5.19"
  )

  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"
}

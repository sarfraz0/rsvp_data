lazy val root = (project in file(".")).
  settings(
    name := "shk-rsvp-data",
    organization := "shk.data.rsvp",
    version := "0.1.9",
    scalaVersion := "2.12.6"
  )

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-classic" % "1.3.0-alpha4",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.2",
  "org.postgresql" % "postgresql" % "42.2.1",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "com.softwaremill.quicklens" % "quicklens_2.12" % "1.4.11"
)

libraryDependencies += "shk.utils" %% "shk-utils" % "0.2.2"

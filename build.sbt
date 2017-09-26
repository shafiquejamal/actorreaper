import Dependencies._

lazy val akkaVersion = "2.+"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.eigenroute",
      scalaVersion := "2.11.11",
      version      := "0.0.1"
    )),
    name := "Hello",
    libraryDependencies ++= Seq(
     "com.typesafe.akka" %% "akka-actor" % akkaVersion,
     "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
      scalaTest % Test
    )
  )

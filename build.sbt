import Dependencies._
import ReleaseTransformations._
import sbt.StdoutOutput

releaseCrossBuild := false
releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  releaseStepCommand("publishSigned"),
  setNextVersion,
  commitNextVersion,
  releaseStepCommand("sonatypeReleaseAll"),
  pushChanges
)

lazy val akkaVersion = "2.+"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.github.shafiquejamal",
      scalaVersion := "2.11.11",
      version      := "0.0.1"
    )),
    name := "ActorReaper",
    libraryDependencies ++= Seq(
     "com.typesafe.akka" %% "akka-actor" % akkaVersion,
     "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
      scalaTest % Test
    )
  )

outputStrategy := Some(StdoutOutput)


useGpg := true
pomIncludeRepository := { _ => false }

licenses := Seq("BSD-style" -> url("http://www.opensource.org/licenses/bsd-license.php"))

homepage := Some(url("https://github.com/shafiquejamal/actorreaper"))

scmInfo := Some(
  ScmInfo(
    url("https://github.com/shafiquejamal/actorreaper"),
    "scm:git@github.com:shafiquejamal/actorreaper.git"
  )
)

developers := List(
  Developer(
    id    = "shafiquejamal",
    name  = "Shafique Jamal",
    email = "admin@eigenroute.com",
    url   = url("http://eigenroute.com")
  )
)

publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false
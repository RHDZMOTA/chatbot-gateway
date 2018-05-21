import Dependencies._

enablePlugins(JavaAppPackaging)

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.rhdzmota",
      scalaVersion := "2.12.6",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "chatbot-gateway",
    libraryDependencies ++= {
      val akkaHttpVersion = "10.0.0"
      val configVersion = "1.3.1"
      val circeVersion = "0.9.3"
      val akkaVersion = "2.4.14"
      Seq(
        "com.typesafe" % "config" % configVersion,
        "io.circe" %% "circe-core" % circeVersion,
        "io.circe" %% "circe-parser" % circeVersion,
        "io.circe" %% "circe-generic" % circeVersion,
        "com.typesafe.akka" %% "akka-actor" % akkaVersion,
        "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
        "com.typesafe.akka" %% "akka-http-core" % akkaHttpVersion,
        scalaTest % Test
      )
    }
  ).dependsOn(fbMessengerProject)

val targetCommit = "661c9a21b0af16f76186b283f3a299ddb4156966"

lazy val fbMessengerProject = RootProject(uri(s"git://github.com/rhdzmota/fb-messenger.git#$targetCommit"))


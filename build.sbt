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
	"com.lightbend.akka" %% "akka-stream-alpakka-google-cloud-pub-sub" % "0.19",
        scalaTest % Test
      )
    }
  ).dependsOn(fbMessengerProject).dependsOn(googlePubSubScalaProject)

val targetCommitFbMessengerProject = "5330eab77efa8e3bcc47042fe159881e1f99dace"
val targetCommitGooglePubsubScala = "c3604f6370e87f63c567b45d029afa3eb9c666db"

lazy val fbMessengerProject = RootProject(uri(s"git://github.com/rhdzmota/fb-messenger.git#$targetCommitFbMessengerProject"))
lazy val googlePubSubScalaProject = RootProject(uri(s"git://github.com/rhdzmota/google-pubsub-scala.git#$targetCommitGooglePubsubScala")) 

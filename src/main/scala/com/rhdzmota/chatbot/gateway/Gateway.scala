package com.rhdzmota.chatbot.gateway

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.http.scaladsl.model.{HttpEntity, ContentTypes}
import akka.http.scaladsl.server.Directives._

import scala.concurrent.{ExecutionContext, Future}

object Gateway extends App {

  def start(): Future[Http.ServerBinding] = {
    implicit val actorSystem: ActorSystem = ActorSystem()
    implicit val executionContext: ExecutionContext = actorSystem.dispatcher
    implicit val actorMaterializer: ActorMaterializer = ActorMaterializer()

    val helloWorldRoute = path("hello") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Hello World!</h1>"))
      }
    }

    Http().bindAndHandle(helloWorldRoute, Settings.Http.host, Settings.Http.port)
  }

  start()

}

package com.rhdzmota.chatbot.gateway

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.rhdzmota.chatbot.gateway.controller.Hello

import scala.concurrent.{ExecutionContext, Future}

object Gateway extends App {

  def start(): Future[Http.ServerBinding] = {
    implicit val actorSystem: ActorSystem = ActorSystem()
    implicit val executionContext: ExecutionContext = actorSystem.dispatcher
    implicit val actorMaterializer: ActorMaterializer = ActorMaterializer()

    val route: Route = pathPrefix("v1") {
      Hello.route
    }
    
    Http().bindAndHandle(route, Settings.Http.host, Settings.Http.port)
  }

  start()

}

package com.rhdzmota.chatbot.gateway

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.Http
import com.rhdzmota.chatbot.gateway.controller.FacebookController
import com.rhdzmota.pubsub.{PubSubConfig, PubSubProducer}

import scala.concurrent.{ExecutionContext, Future}

object Gateway extends Context {

  def publish(data: String, messageId: String, attributes: Option[Map[String, String]]): Option[Future[Seq[Seq[String]]]] =
    PubSubConfig.fromEnv(
      Settings.PubSub.privateKeyLabel,
      Settings.PubSub.projectIdLabel,
      Settings.PubSub.apiIdLabel,
      Settings.PubSub.serviceAccountEmailLabel).map(
        config => PubSubProducer(config).publish(
          Settings.PubSub.fbTopic, data, messageId, attributes))

  val route: Route = pathPrefix("v1") {
    FacebookController(publish).route
  }

  def main(args: Array[String]): Unit = {
    Http().bindAndHandle(route, Settings.Http.host, Settings.Http.port)
  }
}

package com.rhdzmota.chatbot.gateway.controller

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.model.StatusCodes.Forbidden
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._

import com.rhdzmota.chatbot.gateway.service.{WebhookService, FacebookService}
import com.rhdzmota.fbmessenger.webhook.model.Event

import io.circe.parser.decode
import io.circe.generic.auto._
import io.circe.syntax._


sealed trait WebhookController {
  def service: WebhookService
}


case class FacebookController() extends WebhookController {

  val service = FacebookService()

  val route: Route =
    pathPrefix("fb") {
      path("webhook") {
        get {
          parameterMap {params =>
            val challengeResponse: Option[String] = for {
              mode        <- params.get("hub.mode")
              verifyToken <- params.get("hub.verify_token")
              challenge   <- params.get("hub.challenge")
              result      <- service.verify(mode, verifyToken, challenge)
            } yield result

            challengeResponse match {
              case Some(challenge: String) => complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, challenge))
              case None => complete(Forbidden)
            }
          }
        } ~
        post {
          entity(as[String]) {postBody =>
            val webhookEvent = decode[Event](postBody)
            complete(HttpEntity(ContentTypes.`application/json`, postBody))

          }
        }
      }
    }


}

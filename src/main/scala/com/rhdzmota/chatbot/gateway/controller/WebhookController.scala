package com.rhdzmota.chatbot.gateway.controller

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.model.StatusCodes.Forbidden
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._

import com.rhdzmota.chatbot.gateway.service.{WebhookService, FacebookService}
import com.rhdzmota.chatbot.gateway.util.EventSyntax._
import com.rhdzmota.chatbot.gateway.model._
import com.rhdzmota.chatbot.gateway.model.implicits.Encoders._
import com.rhdzmota.chatbot.gateway.model.implicits.Decoders._
import com.rhdzmota.fbmessenger.webhook.model._
import com.rhdzmota.fbmessenger.webhook.model.implicits.Encoders._
import com.rhdzmota.fbmessenger.webhook.model.implicits.Decoders._
import com.rhdzmota.pubsub.{PubSubConfig, PubSubProducer}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import io.circe.syntax._
import io.circe.parser.decode

sealed trait WebhookController {
  def service: WebhookService
}

case class FacebookController(publish: (String, String, Option[Map[String, String]]) => Option[Future[Seq[Seq[String]]]]) extends WebhookController {

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
            decode[Event](postBody) match {
              case Left(error: io.circe.Error) =>
                println(postBody)
                complete(HttpEntity(ContentTypes.`application/json`, s"""{"error": ${error.toString}}"""))
              case Right(webhookEvent: Event)  =>
                val messages: List[CustomMessage] = webhookEvent.toCustomMessages.flatten
                val publishProcedure: List[Option[Future[Seq[Seq[String]]]]] = messages.map(message => {
                  val attachments = message.toMap
                  val messageId = s"${attachments("timestamp")}-${attachments("sender")}-${attachments("receiver")}"
                  val data = attachments.get("type").getOrElse("undefined")
                  publish(data, messageId, Some(attachments))
                })
                publishProcedure.foreach(x => x match {
                  case Some(value) => 
                    println(postBody)
                    println(messages.asJson.toString)
                    println(s"println: publishProcedure = Some(${value.toString})")
                  case None => 
                    println(postBody)
                    println("println: publishProcedure = None")
                })
                val stringResponse = messages.asJson.toString
                complete(HttpEntity(ContentTypes.`application/json`, stringResponse))
            }
          }
        }
      }
    }
}

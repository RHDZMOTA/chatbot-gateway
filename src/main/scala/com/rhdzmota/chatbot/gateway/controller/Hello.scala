package com.rhdzmota.chatbot.gateway.controller

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{Route, StandardRoute}
import com.rhdzmota.chatbot.gateway.service.{English, French, Spanish}

case object Hello {

  def response(hello: String): StandardRoute =
    complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"<h1>$hello</h1>"))

  val route: Route = pathPrefix("hello") {
    pathEnd {
      response(English.speak)
    } ~
    pathPrefix("lang") {
      extractUnmatchedPath {p => p.toString match {
        case en if en contains "en" => response(English.speak)
        case es if es contains "es" => response(Spanish.speak)
        case fr if fr contains "fr" => response(French.speak)
        case _ => response(English.speak)
      }}
    }
  }
}

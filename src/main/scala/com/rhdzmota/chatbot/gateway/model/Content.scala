package com.rhdzmota.chatbot.gateway.model

sealed trait Content {
  def mid: String
  def `type`: String
}

case class Text(mid: String,`type`: String, text: String) extends Content
case class QuickReply(mid: String, `type`: String, payload: String) extends Content
case class Attachment(mid: String, `type`: String, seq: Int,      url: String) extends Content
case class Fallback  (mid: String, `type`: String, title: String, url: String) extends Content
case class Location  (mid: String, `type`: String, lat: Double,   long: Double) extends Content


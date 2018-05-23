package com.rhdzmota.chatbot.gateway.model

sealed trait Content {
  def mid: String
  def `type`: String
  def timestamp: Long
  def toMap: Map[String, String] = this match {
    case CustomText(mid, t, timestamp, text) => Map(
      "mid"       -> mid, 
      "type"      -> t,
      "timestamp" -> timestamp.toString,
      "text"      -> text)
    case CustomQuickReply(mid, t, timestamp, payload) => Map(
      "mid"       -> mid,
      "type"      -> t,
      "timestamp" -> timestamp.toString,
      "payload"   -> payload)
    case CustomAttachment(mid, t, timestamp, seq, url) => Map(
      "mid"       -> mid,
      "type"      -> t,
      "timestamp" -> timestamp.toString,
      "seq"       -> seq.toString,
      "url"       -> url)
    case CustomFallback(mid, t, timestamp, title, url) => Map(
      "mid"       -> mid,
      "type"      -> t,
      "timestamp" -> timestamp.toString,
      "title"     -> title,
      "url"       -> url)
    case CustomLocation(mid, t, timestamp, lat, long) => Map(
      "mid"       -> mid,
      "type"      -> t,
      "timestamp" -> timestamp.toString,
      "lat"       -> lat.toString,
      "long"      -> long.toString)
  }
}

case class CustomText(mid: String,`type`: String, timestamp: Long, text: String) extends Content
case class CustomQuickReply(mid: String, `type`: String, timestamp: Long, payload: String) extends Content
case class CustomAttachment(mid: String, `type`: String, timestamp: Long, seq: Int,      url: String) extends Content
case class CustomFallback  (mid: String, `type`: String, timestamp: Long, title: String, url: String) extends Content
case class CustomLocation  (mid: String, `type`: String, timestamp: Long, lat: Double,   long: Double) extends Content

package com.rhdzmota.chatbot.gateway.model

sealed trait Content {
  def mid: String
  def `type`: String
  def timestamp: Long
}

case class CustomText(mid: String,`type`: String, timestamp: Long, text: String) extends Content
case class CustomQuickReply(mid: String, `type`: String, timestamp: Long, payload: String) extends Content
case class CustomAttachment(mid: String, `type`: String, timestamp: Long, seq: Int,      url: String) extends Content
case class CustomFallback  (mid: String, `type`: String, timestamp: Long, title: String, url: String) extends Content
case class CustomLocation  (mid: String, `type`: String, timestamp: Long, lat: Double,   long: Double) extends Content

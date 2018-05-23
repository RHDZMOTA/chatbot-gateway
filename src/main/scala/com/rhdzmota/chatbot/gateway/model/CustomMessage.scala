package com.rhdzmota.chatbot.gateway.model

case class CustomMessage(sender: String, receiver: String, timestamp: Long, content: Content) {
  def toMap: Map[String, String] = 
    Map("sender" -> sender, "receiver" -> receiver) ++ content.toMap
}


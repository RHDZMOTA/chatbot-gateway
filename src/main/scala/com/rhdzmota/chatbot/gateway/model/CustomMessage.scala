package com.rhdzmota.chatbot.gateway.model

case class CustomMessage(sender: String, receiver: String, timestamp: Long, content: Content)

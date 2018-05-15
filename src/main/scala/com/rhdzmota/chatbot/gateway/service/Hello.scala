package com.rhdzmota.chatbot.gateway.service

sealed trait Hello {
  def speak: String
}

case object Spanish extends Hello {
  override def speak: String = "¡Hola!"
}

case object English extends Hello {
  override def speak: String = "Hello!"
}

case object French extends Hello {
  override def speak: String = "Bonjour!"
}

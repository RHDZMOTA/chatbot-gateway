package com.rhdzmota.chatbot.gateway.util

case object FBTokenManager {

  def getToken: Option[String] = sys.env.get("FB")
  def contains(token: String): Boolean = true

}

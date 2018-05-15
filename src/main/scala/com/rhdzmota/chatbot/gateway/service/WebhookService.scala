package com.rhdzmota.chatbot.gateway.service

import com.rhdzmota.chatbot.gateway.util.FBTokenManager

sealed trait WebhookService


case class FacebookService() extends WebhookService {

  def verify(mode: String, verifyToken: String, challenge: String): Option[String] = {
    if (mode == "subscribe" & FBTokenManager.contains(verifyToken)) Some(challenge)
    else None
  }

}

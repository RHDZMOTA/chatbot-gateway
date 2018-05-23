package com.rhdzmota.chatbot.gateway

import com.typesafe.config.{Config, ConfigFactory}

object Settings {
  private val app: Config = ConfigFactory.load().getConfig("application")

  object Http {
    private val http: Config = app.getConfig("http")
    val host: String = http.getString("host")
    val port: Int = sys.env.getOrElse("PORT", http.getString("port")).toInt
  }

  object PubSub {
    private val pubsub: Config = app.getConfig("pubsub")
    val privateKeyLabel: String = pubsub.getString("privateKeyLabel")
    val projectIdLabel: String = pubsub.getString("projectIdLabel")
    val apiIdLabel: String = pubsub.getString("apiIdLabel")
    val serviceAccountEmailLabel: String = pubsub.getString("serviceAccountEmailLabel")
    val fbTopic = pubsub.getString("fbTopic")
  }
}

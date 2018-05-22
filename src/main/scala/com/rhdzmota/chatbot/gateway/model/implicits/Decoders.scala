package com.rhdzmota.chatbot.gateway.model.implicits

import com.rhdzmota.chatbot.gateway.model._

import io.circe.Decoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveDecoder

object Decoders {
  implicit val customConfig: Configuration = Configuration.default.withSnakeCaseMemberNames.withDefaults
  // CustomMessage
  implicit val decodeCustomMessage: Decoder[CustomMessage] = deriveDecoder[CustomMessage]
  // Content
  implicit val decodeCustomText: Decoder[CustomText] = deriveDecoder[CustomText]
  implicit val decodeCustomQuickReply: Decoder[CustomQuickReply] = deriveDecoder[CustomQuickReply]
  implicit val decodeCustomAttachment: Decoder[CustomAttachment] = deriveDecoder[CustomAttachment]
  implicit val decodeCustomFallback: Decoder[CustomFallback] = deriveDecoder[CustomFallback]
  implicit val decodeCustomLocation: Decoder[CustomLocation] = deriveDecoder[CustomLocation]
  implicit val decodeContent: Decoder[Content] =
    Decoder[CustomLocation].map[Content](identity)
      .or(Decoder[CustomFallback].map[Content](identity))
      .or(Decoder[CustomAttachment].map[Content](identity))
      .or(Decoder[CustomQuickReply].map[Content](identity))
      .or(Decoder[CustomText].map[Content](identity))
}

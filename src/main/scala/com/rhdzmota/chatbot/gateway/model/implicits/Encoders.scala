package com.rhdzmota.chatbot.gateway.model.implicits

import com.rhdzmota.chatbot.gateway.model._

import io.circe.Encoder
import io.circe.syntax._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveEncoder

object Encoders {
  implicit val customConfig: Configuration = Configuration.default.withSnakeCaseMemberNames.withDefaults
  // CustomMessage
  implicit val encodeCustomMessage: Encoder[CustomMessage] = deriveEncoder[CustomMessage]
  // Content
  implicit val encodeCustomText: Encoder[CustomText] = deriveEncoder[CustomText]
  implicit val encodeCustomQuickReply: Encoder[CustomQuickReply] = deriveEncoder[CustomQuickReply]
  implicit val encodeCustomAttachment: Encoder[CustomAttachment] = deriveEncoder[CustomAttachment]
  implicit val encodeCustomFallback: Encoder[CustomFallback] = deriveEncoder[CustomFallback]
  implicit val encodeCustomLocation: Encoder[CustomLocation] = deriveEncoder[CustomLocation]
  implicit val encodeContent: Encoder[Content] = Encoder.instance {
    case customText @ CustomText(_, _, _, _) => customText.asJson
    case customQuickReply @ CustomQuickReply(_, _, _, _) => customQuickReply.asJson
    case customAttachment @ CustomAttachment(_, _, _, _, _) => customAttachment.asJson
    case customFallback @ CustomFallback(_, _, _, _, _) => customFallback.asJson
    case customLocation @ CustomLocation(_, _, _, _, _) => customLocation.asJson
  }
}


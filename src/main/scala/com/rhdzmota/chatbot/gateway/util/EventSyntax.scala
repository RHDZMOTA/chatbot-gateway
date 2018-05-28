package com.rhdzmota.chatbot.gateway.util

import com.rhdzmota.fbmessenger.webhook.model._
import com.rhdzmota.fbmessenger.webhook.model.message._
import com.rhdzmota.fbmessenger.webhook.model.attachment._

import com.rhdzmota.chatbot.gateway.model._

object EventSyntax {

  implicit class EventTransformerOps(event: Event) {

    def toCustomMessages: List[Option[CustomMessage]] = event.entry.flatMap( entry => {
      val entryId = entry.id
      val entryTime = entry.time
      entry.messaging.flatMap(fbMessage => {
          val senderId    = fbMessage.sender.id
          val recipientId = fbMessage.recipient.id
          val timestamp   = fbMessage.timestamp
          val messageContentList: List[Option[Content]] = fbMessage.message match {
            case Text(mid, text, quickReply) => quickReply match {
              case Some(qr) => List(Some(CustomQuickReply(mid, "quickReply", timestamp, qr.payload)))
              case None     => List(Some(CustomText(mid, "text", timestamp, text)))
            }
            case WithAttachment(mid, seq, attachments) =>
              attachments.map(attachment => attachment.payload.map(
                payload => CustomAttachment(mid, attachment.`type`, timestamp, seq, payload.url)))
            case WithFallback(mid, text, attachments)  =>
              attachments.map(attachment => Some(
                CustomFallback(mid, attachment.`type`, timestamp, attachment.title, attachment.URL)))
            case WithLocation(mid, seq, attachments)   =>
              attachments.map(attachment => attachment.payload.map(
                payload => CustomLocation(mid, attachment.`type`, timestamp, payload.coordinates.lat, payload.coordinates.long)))
          }
          messageContentList.map(messageContent => messageContent.map(CustomMessage(senderId, recipientId, entryTime, _)))
        }
      )
    })
  }
}

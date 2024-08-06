package com.fsociety.producer.controller

import com.fsociety.producer.service.NotificationSenderService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/single-notification")
class SendSingleNotification(
    private val notificationSenderService: NotificationSenderService
) {

    @PostMapping("/send")
    fun sendNotification(
        @RequestBody notificationMessage: Map<String, Any>
    ): ResponseEntity<String> {
        val message = notificationMessage["message"] as String
        val totalMessageCount = notificationMessage["totalMessageCount"] as Int?
        val type = notificationMessage["type"] as String?
        if (message != null && totalMessageCount != null) {
            notificationSenderService.sendOneToOneNotification(totalMessageCount, type, message)
            return ResponseEntity(message, HttpStatus.OK)
        }

        return ResponseEntity(message, HttpStatus.BAD_REQUEST)
    }
}
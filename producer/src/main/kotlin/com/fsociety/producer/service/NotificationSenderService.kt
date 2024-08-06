package com.fsociety.producer.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class NotificationSenderService(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    @Value("\${spring.kafka.topic.notification.name}")
    private val topic: String,
) {
    private val logger = LoggerFactory.getLogger(NotificationSenderService::class.java)

    fun sendOneToOneNotification(numberOfNotification: Int, type: String?, message: String) {
        // Send multiple notifications in java  thread
        Runnable {
            for (i in 1..numberOfNotification) {
                if (type != null) {
                    sendOneToOneNotification(type, message)
                } else {
                    sendOneToOneNotification(message)
                }
            }
        }.run()
    }

    private fun sendOneToOneNotification(type: String, message: String) {
        kafkaTemplate.send(topic, type, message)
    }

    private fun sendOneToOneNotification(message: String) {
        kafkaTemplate.send(topic, message)
    }
}
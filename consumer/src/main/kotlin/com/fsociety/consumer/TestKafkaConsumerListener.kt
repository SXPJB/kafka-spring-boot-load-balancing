package com.fsociety.consumer

import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class TestKafkaConsumerListener {
    private val logger = LoggerFactory.getLogger(TestKafkaConsumerListener::class.java)

    @KafkaListener(
        topics = ["\${spring.kafka.topic.notification}"],
        groupId = "\${spring.kafka.consumer.group-id}"
    )
    fun notificationSingleListener(
        @Header(KafkaHeaders.RECEIVED_PARTITION) partition: Int,
        @Header(KafkaHeaders.RECEIVED_KEY) notificationType: String?,
        @Payload message: String
    ) {
        logger.info("Received message: $message from partition: $partition with key: $notificationType")
    }

}
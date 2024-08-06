package com.fsociety.producer.config

import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.common.config.TopicConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaAdmin

@Configuration
class KafkaTopicConfig(
    @Value("\${spring.kafka.bootstrap-servers}")
    private val kafkaBootstrapServers: String,
    @Value("\${spring.kafka.topic.notification.name}")
    private val notificationTopic: String,
    @Value("\${spring.kafka.topic.notification.partitions}")
    private val notificationPartitions: Int,
    @Value("\${spring.kafka.topic.notification.segment.bytes}")
    private val segmentBytes: Int,
    @Value("\${spring.kafka.topic.notification.retention.bytes}")
    private val retentionBytes: Long,
) {

    @Bean
    fun kafkaAdmin(): KafkaAdmin {
        val configs = mapOf(
            AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaBootstrapServers
        )
        return KafkaAdmin(configs)
    }

    @Bean
    fun notificationTopic(): NewTopic {
        return TopicBuilder
            .name(notificationTopic)
            .partitions(notificationPartitions)
            .configs(
                mapOf(
                    TopicConfig.CLEANUP_POLICY_CONFIG to TopicConfig.CLEANUP_POLICY_COMPACT + "," + TopicConfig.CLEANUP_POLICY_DELETE,
                    TopicConfig.RETENTION_MS_CONFIG to "1000", // Retention time in milliseconds
                    TopicConfig.SEGMENT_BYTES_CONFIG to segmentBytes.toString(),
                    TopicConfig.RETENTION_BYTES_CONFIG to retentionBytes.toString(),
                )
            ).build()
    }
}
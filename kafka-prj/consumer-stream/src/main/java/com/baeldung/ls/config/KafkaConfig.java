package com.baeldung.ls.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;


@Configuration
public class KafkaConfig {
    public static final String TOPIC_MATCHES = "topicMatches";
    public static final String TOPIC_STATISTICS = "topicStatistics";

    @Bean
    public NewTopic topicOrders() {
        return TopicBuilder.name(TOPIC_MATCHES)
                .partitions(5)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topicStatistics() {
        return TopicBuilder.name(TOPIC_STATISTICS)
                .partitions(5)
                .replicas(1)
                .build();
    }
}

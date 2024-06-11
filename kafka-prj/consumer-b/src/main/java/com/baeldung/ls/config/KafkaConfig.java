package com.baeldung.ls.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    public static final String TOPIC_ORDERS = "topicOrders";
    public static final String GROUP_ID = "orders-group";

    @Bean
    public NewTopic topicOrders() {
        return TopicBuilder.name(TOPIC_ORDERS)
                .partitions(5)
                .replicas(1)
                .build();
    }
}

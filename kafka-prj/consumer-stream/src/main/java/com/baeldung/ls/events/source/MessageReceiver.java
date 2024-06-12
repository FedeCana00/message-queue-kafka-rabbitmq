package com.baeldung.ls.events.source;

import com.baeldung.ls.config.KafkaConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {
    private static final Logger LOG = LoggerFactory.getLogger(MessageReceiver.class);

    public MessageReceiver() {}

    @KafkaListener(topics = KafkaConfig.TOPIC_STATISTICS)
    public void receive(ConsumerRecord<String, Object> record) {
        LOG.info(String.format("Football match filtered: %s", record.value().toString()));
    }
}

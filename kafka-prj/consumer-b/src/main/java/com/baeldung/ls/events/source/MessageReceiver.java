package com.baeldung.ls.events.source;

import com.baeldung.ls.config.KafkaConfig;
import com.baeldung.ls.events.model.Dress;
import com.baeldung.ls.events.model.Order;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {
    private static final Logger LOG = LoggerFactory.getLogger(MessageReceiver.class);

    public MessageReceiver() {}

    @KafkaListener(topics = KafkaConfig.TOPIC_ORDERS, groupId = KafkaConfig.GROUP_ID)
    public void receive(ConsumerRecord<String, Order<String, Dress>> record) {
        String key = record.key();
        Order<String, Dress> value = record.value();
        int partition = record.partition();
        long offset = record.offset();
        String topic = record.topic();

        LOG.info(String.format("Topic=%s, partition=%d, offset=%d, key=%s, value=%s", topic, partition, offset, key, value.toString()));
    }
}

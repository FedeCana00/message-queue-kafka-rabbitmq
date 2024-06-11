package com.baeldung.ls.events.source;

import com.baeldung.ls.config.KafkaConfig;
import com.baeldung.ls.events.model.Dress;
import com.baeldung.ls.events.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {
    private static final Logger LOG = LoggerFactory.getLogger(MessageReceiver.class);

    public MessageReceiver() {}

    @KafkaListener(topics = KafkaConfig.TOPIC_ORDERS, groupId = KafkaConfig.GROUP_ID)
    public void randomMessage(Order<String, Dress> order) {
        LOG.info(order.toString());
    }
}

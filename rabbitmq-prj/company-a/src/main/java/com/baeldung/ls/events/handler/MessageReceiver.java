package com.baeldung.ls.events.handler;

import com.baeldung.ls.config.RabbitMQConfig;
import com.baeldung.ls.events.model.Dress;
import com.baeldung.ls.events.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageReceiver {

    private static final Logger LOG = LoggerFactory.getLogger(MessageReceiver.class);

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME_1)
    public void messageProcessor(Message message) {
        LOG.info(SerializationUtils.deserialize(message.getBody()).toString());
    }
}

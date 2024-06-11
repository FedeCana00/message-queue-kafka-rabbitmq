package com.baeldung.ls.events.handler;

import com.baeldung.ls.config.RabbitMQConfig;
import com.baeldung.ls.events.model.Dress;
import com.baeldung.ls.events.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class MessageReceiver {

    private static final Logger LOG = LoggerFactory.getLogger(MessageReceiver.class);
    private static final String X_RETRIES_HEADER = "x-retries";
    private static final String X_DELAY_HEADER = "x-delay";
    private static final int DELAY_TIME = 5000; // in milliseconds
    private static final int MAX_RETRIES = 3;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_DL)
    public void messageProcessor(Message message) {
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        Integer retries = (Integer) headers.get(X_RETRIES_HEADER);

        if(retries == null)
            retries = 0;

        if(retries < MAX_RETRIES){
            Order<String, Dress> order = (Order<String, Dress>) SerializationUtils.deserialize(message.getBody());
            Message msg = MessageBuilder.withBody(SerializationUtils.serialize(order))
                    .setHeader(X_RETRIES_HEADER, ++retries)
                    .setHeader(X_DELAY_HEADER, DELAY_TIME * retries)
                    .build();

            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, message.getMessageProperties().getReceivedRoutingKey(), msg);
            LOG.info(String.format("[RETRIES %d] %s", retries, order));
            return;
        }

        LOG.info(String.format("NOT SENT [RETRIES %d] %s", retries, message));
    }
}

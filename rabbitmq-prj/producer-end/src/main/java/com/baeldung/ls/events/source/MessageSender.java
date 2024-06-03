package com.baeldung.ls.events.source;

import com.baeldung.ls.config.RabbitMQConfig;
import com.baeldung.ls.events.model.Dress;
import com.baeldung.ls.events.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.random.RandomGenerator;

@Component
public class MessageSender {
    private static final RandomGenerator RANDOM = RandomGenerator.getDefault();
    private static final Logger LOG = LoggerFactory.getLogger(MessageSender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public MessageSender() {}

    @Scheduled(fixedRate = 1000)
    public void randomMessage() {
        Order<String, Dress> order = new Order<>(
                Order.PaymentMethod.class.getEnumConstants()[RANDOM.nextInt(Order.PaymentMethod.class.getEnumConstants().length)],
                UUID.randomUUID().toString(),
                new Dress(
                        Dress.Type.get(RANDOM.nextInt(Dress.Type.size())),
                        Dress.Gender.get(RANDOM.nextInt(Dress.Gender.size())),
                        Dress.Size.get(RANDOM.nextInt(Dress.Size.size())))
        );

        String routingKey = order.getRoutingKey();
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, routingKey, order);
        LOG.info(String.format("[ORDER SENT with key %s] %s", routingKey, order));
    }
}

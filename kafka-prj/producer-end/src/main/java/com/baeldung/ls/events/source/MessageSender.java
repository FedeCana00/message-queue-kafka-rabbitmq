package com.baeldung.ls.events.source;

import com.baeldung.ls.config.KafkaConfig;
import com.baeldung.ls.events.model.Dress;
import com.baeldung.ls.events.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.random.RandomGenerator;

@Component
public class MessageSender {
    private static final RandomGenerator RANDOM = RandomGenerator.getDefault();
    private static final Logger LOG = LoggerFactory.getLogger(MessageSender.class);

    @Autowired
    private KafkaTemplate<String, Order<String, Dress>> kafkaTemplate;

    public MessageSender() {}

    @Scheduled(fixedRate = 200)
    public void randomMessage() {
        try {
            Order<String, Dress> order = new Order<>(
                    Order.PaymentMethod.class.getEnumConstants()[RANDOM.nextInt(Order.PaymentMethod.class.getEnumConstants().length)],
                    UUID.randomUUID().toString(),
                    new Dress(
                            Dress.Type.get(RANDOM.nextInt(Dress.Type.size())),
                            Dress.Gender.get(RANDOM.nextInt(Dress.Gender.size())),
                            Dress.Size.get(RANDOM.nextInt(Dress.Size.size())))
            );

            String routingKey = order.getRoutingKey();

            kafkaTemplate.send(KafkaConfig.TOPIC_ORDERS, routingKey, order);
            LOG.info(String.format("[ORDER SENT with key %s] %s", routingKey, order));
        } catch (Exception e){
            LOG.error(e.getMessage());
        }
    }
}

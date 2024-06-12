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
import java.util.concurrent.CompletableFuture;
import java.util.random.RandomGenerator;

@Component
public class MessageSender {
    private static final RandomGenerator RANDOM = RandomGenerator.getDefault();
    private static final Logger LOG = LoggerFactory.getLogger(MessageSender.class);
    public static final int MAX_ATTEMPTS = 3;

    @Autowired
    private KafkaTemplate<String, Order<String, Dress>> kafkaTemplate;

    public MessageSender() {}

    @Scheduled(fixedRate = 200)
    public void sendRandomMessage() {
        CompletableFuture.runAsync(this::sendMessage);
    }

    public void sendMessage() {
        int attempt = 1;
        while (attempt <= MAX_ATTEMPTS) {
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

                kafkaTemplate.send(KafkaConfig.TOPIC_ORDERS, routingKey, order).get();

                LOG.info(String.format("[ORDER SENT with key %s] %s", routingKey, order));
                return;
            } catch (Exception e) {
                System.err.println("Error sending message to Kafka: " + e.getMessage());
                attempt++;
            }
        }
        throw new RuntimeException("Sending the message to Kafka failed after " + attempt + " attempts");
    }

    public void setKafkaTemplate(KafkaTemplate<String, Order<String, Dress>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
}

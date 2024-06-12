package com.baeldung.ls.events.source;

import com.baeldung.ls.config.KafkaConfig;
import com.baeldung.ls.events.data.Teams;
import com.baeldung.ls.events.model.FootballMatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.util.concurrent.CompletableFuture;
import java.util.random.RandomGenerator;

@Component
public class MessageSender {
    private static final RandomGenerator RANDOM = RandomGenerator.getDefault();
    private static final Logger LOG = LoggerFactory.getLogger(MessageSender.class);
    public static final int MAX_ATTEMPTS = 3;

    @Autowired
    private KafkaTemplate<String, FootballMatch> kafkaTemplate;

    public MessageSender() {}

    @Scheduled(fixedRate = 200)
    public void sendRandomMessage() {
        CompletableFuture.runAsync(this::sendMessage);
    }

    public void sendMessage() {
        int attempt = 1;
        while (attempt <= MAX_ATTEMPTS) {
            try {
                FootballMatch footballMatch = new FootballMatch(Teams.TEAMS.get(RANDOM.nextInt(Teams.TEAMS.size())), RANDOM.nextInt(5), RANDOM.nextInt(5), Teams.TEAMS.get(RANDOM.nextInt(Teams.TEAMS.size())));
                kafkaTemplate.send(KafkaConfig.TOPIC_MATCHES, footballMatch.getKey(), footballMatch).get();
                LOG.info(String.format("[MATCH SENT] %s", footballMatch));
                return;
            } catch (Exception e) {
                System.err.println("Error sending message to Kafka: " + e.getMessage());
                attempt++;
            }
        }
        throw new RuntimeException("Sending the message to Kafka failed after " + attempt + " attempts");
    }
}

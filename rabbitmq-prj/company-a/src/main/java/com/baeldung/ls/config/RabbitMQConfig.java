package com.baeldung.ls.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE_NAME = "topicExchange";
    public static final String ALTERNATE_EXCHANGE = "alternateExchange";
    public static final String QUEUE_NAME_1 = "queue1";
    public static final String QUEUE_NAME_UNROUTED = "queueUnrouted";
    public static final String ROUTING_KEY_1A = "*.man.*";
    public static final String ROUTING_KEY_1B = "hat.#";

    @Bean
    public FanoutExchange alternateExchange() {
        return new FanoutExchange(ALTERNATE_EXCHANGE);
    }

    @Bean
    public TopicExchange topicExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME)
                .durable(true)
                .alternate(ALTERNATE_EXCHANGE)
                .build();
    }

    @Bean
    public Queue queue1() {
        return new Queue(QUEUE_NAME_1, false);
    }

    @Bean
    public Queue queueUnrouted() {
        return new Queue(QUEUE_NAME_UNROUTED, false);
    }

    @Bean
    public Binding binding1a(Queue queue1, TopicExchange exchange) {
        return BindingBuilder.bind(queue1).to(exchange).with(ROUTING_KEY_1A);
    }

    @Bean
    public Binding binding1b(Queue queue1, TopicExchange exchange) {
        return BindingBuilder.bind(queue1).to(exchange).with(ROUTING_KEY_1B);
    }

    @Bean
    public Binding bindingUnrouted(Queue queueUnrouted, FanoutExchange alternateExchange) {
        return BindingBuilder.bind(queueUnrouted).to(alternateExchange);
    }

    @Bean
    public SimpleMessageConverter converter() {
        SimpleMessageConverter converter = new SimpleMessageConverter();
        // without setting recognized patterns for deserialization, a @RabbitListener failed to convert message error is returned
        converter.setAllowedListPatterns(List.of("com.baeldung.ls.events.model.*", "java.*"));
        return converter;
    }
}

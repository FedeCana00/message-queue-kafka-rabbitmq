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
    public static final String DL_EXCHANGE = "dlExchange";
    public static final String QUEUE_NAME_1 = "queue1";
    public static final String QUEUE_NAME_2 = "queue2";
    public static final String QUEUE_DL = "queueDL";
    public static final String ROUTING_KEY_1A = "*.man.*";
    public static final String ROUTING_KEY_1B = "hat.#";
    public static final String ROUTING_KEY_2 = "*.woman.*";
    public static final long MAX_QUEUE_LENGTH = 10;

    @Bean
    public FanoutExchange alternateExchange() {
        return new FanoutExchange(ALTERNATE_EXCHANGE);
    }

    @Bean
    public FanoutExchange dlExchange() {
        return new FanoutExchange(DL_EXCHANGE);
    }

    @Bean
    public TopicExchange topicExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME)
                .durable(true)
                //.delayed()
                .alternate(ALTERNATE_EXCHANGE)
                .build();
    }

    @Bean
    public Queue queue1() {
        return QueueBuilder.nonDurable(QUEUE_NAME_1)
                .deadLetterExchange(DL_EXCHANGE)
                .maxLength(MAX_QUEUE_LENGTH)
                .build();
    }

    @Bean
    public Queue queue2() {
        return QueueBuilder.nonDurable(QUEUE_NAME_2)
                .deadLetterExchange(DL_EXCHANGE)
                .maxLength(MAX_QUEUE_LENGTH)
                .build();
    }

    @Bean
    public Queue queueDL() {
        return new Queue(QUEUE_DL, false);
    }

    @Bean
    public Binding binding1a(Queue queue1, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue1).to(topicExchange).with(ROUTING_KEY_1A);
    }

    @Bean
    public Binding binding1b(Queue queue1, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue1).to(topicExchange).with(ROUTING_KEY_1B);
    }

    @Bean
    public Binding binding2(Queue queue2, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue2).to(topicExchange).with(ROUTING_KEY_2);
    }

    @Bean
    public Binding bindingDLExchange(Queue queueDL, FanoutExchange dlExchange) {
        return BindingBuilder.bind(queueDL).to(dlExchange);
    }

    @Bean
    public SimpleMessageConverter converter() {
        SimpleMessageConverter converter = new SimpleMessageConverter();
        // without setting recognized patterns for deserialization, a @RabbitListener failed to convert message error is returned
        converter.setAllowedListPatterns(List.of("com.baeldung.ls.events.model.*", "java.*"));
        return converter;
    }
}

package com.baeldung.ls.common;

import com.baeldung.ls.events.model.Dress;
import com.baeldung.ls.events.model.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.common.serialization.Serializer;

public class OrderSerializer implements Serializer<Order<String, Dress>> {

    @Override
    public byte[] serialize(String topic, Order<String, Dress> data) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}


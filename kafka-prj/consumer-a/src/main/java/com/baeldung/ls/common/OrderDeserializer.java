package com.baeldung.ls.common;

import com.baeldung.ls.events.model.Dress;
import com.baeldung.ls.events.model.Order;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class OrderDeserializer implements Deserializer<Order<String, Dress>> {

    @Override
    public Order<String, Dress> deserialize(String topic, byte[] data) {
        try {
            // Deserialize the byte array into an Order object
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.readValue(data, Order.class);
        } catch (IOException e) {
            throw new SerializationException("Error deserializing Order", e);
        }
    }
}

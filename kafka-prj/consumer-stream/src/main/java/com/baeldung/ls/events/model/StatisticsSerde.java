package com.baeldung.ls.events.model;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class StatisticsSerde implements Serde<Statistics> {

    private final Serde<Statistics> inner;

    public StatisticsSerde() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        inner = Serdes.serdeFrom(new JsonSerializer<>(objectMapper), new JsonDeserializer<>(Statistics.class, objectMapper));
    }

    @Override
    public Serializer<Statistics> serializer() {
        return inner.serializer();
    }

    @Override
    public Deserializer<Statistics> deserializer() {
        return inner.deserializer();
    }
}


package com.baeldung.ls.events.model;

import com.fasterxml.jackson.databind.DeserializationFeature;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.Deserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class FootballMatchSerde implements Serde<FootballMatch> {

    private final Serde<FootballMatch> inner;

    public FootballMatchSerde() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        inner = Serdes.serdeFrom(new JsonSerializer<>(objectMapper), new JsonDeserializer<>(FootballMatch.class, objectMapper));
    }

    @Override
    public Serializer<FootballMatch> serializer() {
        return inner.serializer();
    }

    @Override
    public Deserializer<FootballMatch> deserializer() {
        return inner.deserializer();
    }
}


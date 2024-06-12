package com.baeldung.ls.controllers;

import com.baeldung.ls.config.KStreamProcessor;
import com.baeldung.ls.config.KafkaStreamsConfig;
import com.baeldung.ls.events.model.Statistics;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
public class StreamsController {
    private final StreamsBuilderFactoryBean factoryBean;

    public StreamsController(StreamsBuilderFactoryBean factoryBean) {
        this.factoryBean=factoryBean;
    }

    @GetMapping("/ManchesterCity")
    public String getTeamStatistics(){
        KafkaStreams kafkaStreams =  factoryBean.getKafkaStreams();

        ReadOnlyKeyValueStore<String, Statistics> statistics = kafkaStreams
                .store(StoreQueryParameters.fromNameAndType(KafkaStreamsConfig.STATISTICS_KEYS_STORE, QueryableStoreTypes.keyValueStore()));
        return String.format("%s statistics: %s", KStreamProcessor.TEAM_STATISTICS, statistics.get(KStreamProcessor.TEAM_STATISTICS));
    }

}

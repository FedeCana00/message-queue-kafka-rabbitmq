package com.baeldung.ls.config;

import com.baeldung.ls.events.model.FootballMatch;
import com.baeldung.ls.events.model.Statistics;
import com.baeldung.ls.events.model.StatisticsSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.Stores;
import org.springframework.stereotype.Component;

@Component
public class KTableProcessor {

    // KTABLE STATE: Create a KTable for statistics of a team
    public void process(KStream<String, FootballMatch> stream){
        // Create a new KeyValue Store
        KeyValueBytesStoreSupplier teamStatistics = Stores.inMemoryKeyValueStore(KafkaStreamsConfig.STATISTICS_KEYS_STORE);

        KGroupedStream<String, Statistics> teamByStatistics = stream
                .map((key, footballMatch) -> new KeyValue<>(footballMatch.getHomeTeam().getName()
                        , new Statistics(footballMatch.getHomeTeam()
                            , footballMatch.getHomeScore() > footballMatch.getGuestScore() ? 1 : 0
                            , footballMatch.getHomeScore() < footballMatch.getGuestScore() ? 1 : 0
                            , footballMatch.getHomeScore()
                            , footballMatch.getGuestScore())
                )).groupByKey();

        KTable<String, Statistics> statisticsKTable = teamByStatistics.aggregate(Statistics::new,
                (k,v, aggregate) -> {
                    aggregate.setTeam(v.getTeam());
                    aggregate.setWins(aggregate.getWins() + v.getWins());
                    aggregate.setLosses(aggregate.getLosses() + v.getLosses());
                    aggregate.setGoalsScore(aggregate.getGoalsScore() + v.getGoalsScore());
                    aggregate.setGoalsConceded(aggregate.getGoalsConceded() + v.getGoalsConceded());
                    return aggregate;
                }, Materialized.with(Serdes.String(), new StatisticsSerde()));

        final KTable<String, Statistics> statistics = statisticsKTable.mapValues(value -> value, Materialized.as(teamStatistics));
    }
}

package com.baeldung.ls.config;

import com.baeldung.ls.events.data.Teams;
import com.baeldung.ls.events.model.FootballMatch;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.stereotype.Component;


@Component
public class KStreamProcessor {

    public static final String TEAM_STATISTICS = Teams.TEAMS.get(0).getName();

    public void process(KStream<String, FootballMatch> stream){

        // KSTREAM FILTER: Filter the Stream to get football matches of a team
        stream.filter((key, object) -> object != null && object.getHomeTeam().getName().equals(TEAM_STATISTICS)).to(KafkaConfig.TOPIC_STATISTICS);
    }
}

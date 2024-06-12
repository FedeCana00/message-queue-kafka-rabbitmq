package com.baeldung.ls.events.data;

import com.baeldung.ls.events.model.FootballTeam;

import java.util.Arrays;
import java.util.List;

public class Teams {
    public static final List<FootballTeam> TEAMS = Arrays.asList(
            new FootballTeam("Manchester City", "England", "Premier League"),
            new FootballTeam("Manchester United", "England", "Premier League"),
            new FootballTeam("PSG", "France", "Ligue 1"),
            new FootballTeam("Milan", "Italy", "Serie A"),
            new FootballTeam("Inter","Italy", "Serie A"),
            new FootballTeam("Bayern Monaco", "Germany", "Bundesliga"),
            new FootballTeam("Barcellona", "Spain", "Liga"),
            new FootballTeam("Real Madrid", "Spain", "Liga"));
}

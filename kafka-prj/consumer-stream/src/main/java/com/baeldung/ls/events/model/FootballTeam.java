package com.baeldung.ls.events.model;

import java.io.Serializable;

public class FootballTeam implements Serializable {
    private String name;
    private String county;
    private String league;

    public FootballTeam() {}

    public FootballTeam(String name, String county, String league) {
        this.name = name;
        this.county = county;
        this.league = league;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }
}

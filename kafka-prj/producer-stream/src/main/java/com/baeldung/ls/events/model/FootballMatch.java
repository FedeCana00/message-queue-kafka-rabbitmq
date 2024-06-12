package com.baeldung.ls.events.model;

import java.io.Serializable;

public class FootballMatch implements Serializable {
    private FootballTeam homeTeam;
    private int homeScore;
    private int guestScore;
    private FootballTeam guestTeam;

    public FootballMatch() {}

    public FootballMatch(FootballTeam homeTeam, int homeScore, int guestScore, FootballTeam guestTeam) {
        this.homeTeam = homeTeam;
        this.homeScore = homeScore;
        this.guestScore = guestScore;
        this.guestTeam = guestTeam;
    }

    public String getKey(){
        return String.format("%s %d:%d %s", homeTeam.getName(), homeScore, guestScore, guestTeam.getName());
    }

    public FootballTeam getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(FootballTeam homeTeam) {
        this.homeTeam = homeTeam;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public int getGuestScore() {
        return guestScore;
    }

    public void setGuestScore(int guestScore) {
        this.guestScore = guestScore;
    }

    public FootballTeam getGuestTeam() {
        return guestTeam;
    }

    public void setGuestTeam(FootballTeam guestTeam) {
        this.guestTeam = guestTeam;
    }

    @Override
    public String toString() {
        return String.format("%s %d:%d %s", homeTeam.getName(), homeScore, guestScore, guestTeam.getName());
    }
}

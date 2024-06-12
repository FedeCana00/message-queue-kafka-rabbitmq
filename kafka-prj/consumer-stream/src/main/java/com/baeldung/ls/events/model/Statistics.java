package com.baeldung.ls.events.model;

import java.io.Serializable;

public class Statistics implements Serializable {
    private FootballTeam team;
    private int wins;
    private int losses;
    private int goalsScore;
    private int goalsConceded;

    public Statistics() {}

    public Statistics(FootballTeam team, int wins, int losses, int goalsScore, int goalsConceded) {
        this.team = team;
        this.wins = wins;
        this.losses = losses;
        this.goalsScore = goalsScore;
        this.goalsConceded = goalsConceded;
    }

    public FootballTeam getTeam() {
        return team;
    }

    public void setTeam(FootballTeam team) {
        this.team = team;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getGoalsScore() {
        return goalsScore;
    }

    public void setGoalsScore(int goalsScore) {
        this.goalsScore = goalsScore;
    }

    public int getGoalsConceded() {
        return goalsConceded;
    }

    public void setGoalsConceded(int goalsConceded) {
        this.goalsConceded = goalsConceded;
    }

    @Override
    public String toString() {
        return String.format("%s statistics:\n\twin=%d\n\tlosses=%d\n\tgoals scored=%d\n\tgoals conceded=%d", team == null ? "" : team.getName(), wins, losses, goalsScore, goalsConceded);
    }
}

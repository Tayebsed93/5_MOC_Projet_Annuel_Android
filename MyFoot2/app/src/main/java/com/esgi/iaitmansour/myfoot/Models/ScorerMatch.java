package com.esgi.iaitmansour.myfoot.Models;

/**
 * Created by iaitmansour on 03/07/2018.
 */

public class ScorerMatch {

    public String time;
    public String homeScorer;
    public String awayScorer;

    public ScorerMatch() {
    }

    public ScorerMatch(String time, String homeScorer, String awayScorer) {
        this.time = time;
        this.homeScorer = homeScorer;
        this.awayScorer = awayScorer;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHomeScorer() {
        return homeScorer;
    }

    public void setHomeScorer(String homeScorer) {
        this.homeScorer = homeScorer;
    }

    public String getAwayScorer() {
        return awayScorer;
    }

    public void setAwayScorer(String awayScorer) {
        this.awayScorer = awayScorer;
    }
}

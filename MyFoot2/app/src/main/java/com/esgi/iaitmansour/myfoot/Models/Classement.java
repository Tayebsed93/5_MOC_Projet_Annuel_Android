package com.esgi.iaitmansour.myfoot.Models;

/**
 * Created by iaitmansour on 30/06/2018.
 */

public class Classement {

    public String overall_league_position;
    public String team_name;
    public String overall_league_PTS;
    public String overall_league_payed;
    public String overall_league_W;
    public String overall_league_D;
    public String overall_league_L;
    public String overall_league_GF;
    public String overall_league_GA;

    public Classement(){

    }

    public Classement(String overall_league_position, String team_name, String overall_league_PTS, String overall_league_payed, String overall_league_W, String overall_league_D, String overall_league_L, String overall_league_GF, String overall_league_GA) {
        this.overall_league_position = overall_league_position;
        this.team_name = team_name;
        this.overall_league_PTS = overall_league_PTS;
        this.overall_league_payed = overall_league_payed;
        this.overall_league_W = overall_league_W;
        this.overall_league_D = overall_league_D;
        this.overall_league_L = overall_league_L;
        this.overall_league_GF = overall_league_GF;
        this.overall_league_GA = overall_league_GA;
    }

    public String getOverall_league_position() {
        return overall_league_position;
    }

    public void setOverall_league_position(String overall_league_position) {
        this.overall_league_position = overall_league_position;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getOverall_league_PTS() {
        return overall_league_PTS;
    }

    public void setOverall_league_PTS(String overall_league_PTS) {
        this.overall_league_PTS = overall_league_PTS;
    }

    public String getOverall_league_payed() {
        return overall_league_payed;
    }

    public void setOverall_league_payed(String overall_league_payed) {
        this.overall_league_payed = overall_league_payed;
    }

    public String getOverall_league_W() {
        return overall_league_W;
    }

    public void setOverall_league_W(String overall_league_W) {
        this.overall_league_W = overall_league_W;
    }

    public String getOverall_league_D() {
        return overall_league_D;
    }

    public void setOverall_league_D(String overall_league_D) {
        this.overall_league_D = overall_league_D;
    }

    public String getOverall_league_L() {
        return overall_league_L;
    }

    public void setOverall_league_L(String overall_league_L) {
        this.overall_league_L = overall_league_L;
    }

    public String getOverall_league_GF() {
        return overall_league_GF;
    }

    public void setOverall_league_GF(String overall_league_GF) {
        this.overall_league_GF = overall_league_GF;
    }

    public String getOverall_league_GA() {
        return overall_league_GA;
    }

    public void setOverall_league_GA(String overall_league_GA) {
        this.overall_league_GA = overall_league_GA;
    }
}

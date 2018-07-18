package com.esgi.iaitmansour.myfoot.Models;

/**
 * Created by iaitmansour on 04/07/2018.
 */

public class CompositionMatch {

    public String lineup_player;
    public String lineup_number;


    public CompositionMatch() {
    }

    public CompositionMatch(String lineup_player, String lineup_number) {
        this.lineup_player = lineup_player;
        this.lineup_number = lineup_number;
    }

    public String getLineup_player() {
        return lineup_player;
    }

    public void setLineup_player(String lineup_player) {
        this.lineup_player = lineup_player;
    }

    public String getLineup_number() {
        return lineup_number;
    }

    public void setLineup_number(String lineup_number) {
        this.lineup_number = lineup_number;
    }
}

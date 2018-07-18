package com.esgi.iaitmansour.myfoot.Models;

import android.support.annotation.NonNull;

/**
 * Created by iaitmansour on 12/07/2018.
 */

public class MatchGame implements  Comparable {

    public int id;
    public String matchHome;
    public String matchAway;
    public String groupe;
    public String compositionName;
    public String timeStart;
    public int userId;


    public MatchGame() {
    }

    public MatchGame(int id, String matchHome, String matchAway, String groupe, String compositionName, String timeStart, int userId) {
        this.id = id;
        this.matchHome = matchHome;
        this.matchAway = matchAway;
        this.groupe = groupe;
        this.compositionName = compositionName;
        this.timeStart = timeStart;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }


    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatchHome() {
        return matchHome;
    }

    public void setMatchHome(String matchHome) {
        this.matchHome = matchHome;
    }

    public String getMatchAway() {
        return matchAway;
    }

    public void setMatchAway(String matchAway) {
        this.matchAway = matchAway;
    }

    public String getGroupe() {
        return groupe;
    }

    public void setGroupe(String groupe) {
        this.groupe = groupe;
    }

    public String getCompositionName() {
        return compositionName;
    }

    public void setCompositionName(String compositionName) {
        this.compositionName = compositionName;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    @Override
    public int compareTo(@NonNull Object o) {

        int compareId =((MatchGame)o).getId ();
        return this.id-compareId;

    }
}

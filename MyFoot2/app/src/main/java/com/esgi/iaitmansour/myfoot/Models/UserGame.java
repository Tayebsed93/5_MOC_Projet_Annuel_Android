package com.esgi.iaitmansour.myfoot.Models;

/**
 * Created by iaitmansour on 05/07/2018.
 */

public class UserGame {

    private String name;
    private int score;
    private String urlImgUser;


    public UserGame() {
    }

    public UserGame(String name, int score, String urlImgUser) {
        this.name = name;
        this.score = score;
        this.urlImgUser = urlImgUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUrlImgUser() {
        return urlImgUser;
    }

    public void setUrlImgUser(String urlImgUser) {
        this.urlImgUser = urlImgUser;
    }
}

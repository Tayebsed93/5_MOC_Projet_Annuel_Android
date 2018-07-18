package com.esgi.iaitmansour.myfoot.Models;

/**
 * Created by iaitmansour on 28/06/2018.
 */

public class ExistingClubs {

    private int userId;
    private String nom;
    private String logoUrl;

    public ExistingClubs(int userId, String nom, String logoUrl) {
        this.userId = userId;
        this.nom = nom;
        this.logoUrl = logoUrl;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}

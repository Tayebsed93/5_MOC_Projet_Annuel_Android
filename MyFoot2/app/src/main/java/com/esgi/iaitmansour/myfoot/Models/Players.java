package com.esgi.iaitmansour.myfoot.Models;

/**
 * Created by iaitmansour on 14/07/2018.
 */

public class Players {

    public String name;
    public int age;
    public String pays;


    public Players(String name, int age, String pays) {
        this.name = name;
        this.age = age;
        this.pays = pays;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }
}

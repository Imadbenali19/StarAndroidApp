package com.example.appstar.beans;

public class Team {

    private int id;
    private static int c=0;
    private String name;
    private String image;
    private String league;
    private String hisory;
    private float star;



    public Team(String name, String image, String league, String hisory, float star) {
        this.star = star;
        this.id = ++c;
        this.name = name;
        this.image = image;
        this.league = league;
        this.hisory = hisory;
    }

    public int getId() {
        return id;
    }

    public static int getC() {
        return c;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getLeague() {
        return league;
    }

    public String getHisory() {
        return hisory;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static void setC(int c) {
        Team.c = c;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public void setHisory(String hisory) {
        this.hisory = hisory;
    }

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }

}

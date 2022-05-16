package com.geektach.newsapp42.models;

public class Board {


    private String title;
    private String desc;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public Board(String title, String desc, int image) {
        this.title = title;
        this.desc = desc;
    }
}

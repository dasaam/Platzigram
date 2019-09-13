package com.example.dakane.platzigram.model;

/**
 * Created by dakane on 03/09/19.
 */

public class Picture {
    private String picture;
    private String username;
    private String time;
    private String likeNumber = "0";

    public Picture(String picture, String username, String likeNumber, String time) {
        this.picture = picture;
        this.username = username;
        this.likeNumber = likeNumber;
        this.time = time;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(String likeNumber) {
        this.likeNumber = likeNumber;
    }
}

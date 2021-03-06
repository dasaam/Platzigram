package com.example.dakane.platzigram.model;

import android.text.format.DateUtils;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by dakane on 12/02/20.
 */

public class Post {
    public String uid;
    public String author;
    public String imageUrl;
    public double timestampCreated;

    public Post() {
    }

    public Post(String author, String imageUrl, double timestampCreated) {
        this.author = author;
        this.imageUrl = imageUrl;
        this.timestampCreated = timestampCreated;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getTimestampCreated() {
        return timestampCreated;
    }

    public void setTimestampCreated(double timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

    /*public String getRelativeTimeStamp(){
        return DateUtils.getRelativeTimeSpanString((long)this.timestampCreated,
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_WEEKDAY).toString();
    }*/
}

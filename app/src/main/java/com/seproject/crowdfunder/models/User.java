package com.seproject.crowdfunder.models;

import java.util.ArrayList;
import java.util.Iterator;

public class User {

    String uid;
    String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    String place;

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    float rating;

    public User(String uid, String email, String place) {
        this.uid = uid;
        this.email = email;
        this.place = place;
    }

    public User() {
        this.uid = "";
        this.email = "";
        this.place = "";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

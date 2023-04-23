package com.example.civiladvocacyapp;

import java.io.Serializable;

public class PoliticianObject implements Serializable {
    private String title;
    private String name;
    private String party;
    private String photo;

    private String twitter;
    private String facebook;
    private String youtube;

    private String address;
    private String phone;
    private String email;
    private String website;
    // Include possible an array list that has all the details

    public PoliticianObject(String t, String n, String p, String ph, String tw, String fb, String yt, String add, String pho, String em, String web) {
        title = t;
        name = n;
        party = p;
        photo = ph;
        twitter = tw;
        facebook = fb;
        youtube = yt;
        address = add;
        phone = pho;
        email = em;
        website = web;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public String getParty() {
        return party;
    }

    public String getPhoto() {
        return photo;
    }

    public String getTwitter() { return twitter; }

    public String getFacebook() { return facebook; }

    public String getYoutube() { return youtube; }

    public String getAddress() { return address; }

    public String getPhone() { return phone; }

    public String getEmail() { return email; }

    public String getWebsite() { return website; }
}

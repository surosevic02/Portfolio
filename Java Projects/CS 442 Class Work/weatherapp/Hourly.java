package com.example.weatherapp;

import java.io.Serializable;

public class Hourly implements Serializable {
    private String day;
    private String time;
    //private static String icon;
    private String temp;
    private String conditions;
    private String icon;

    public Hourly(String d, String t, String tmp, String c, String i) {
        day = d;
        time = t;
        icon = i;
        temp = tmp;
        conditions = c;
    }

    public String getDay() { return day; }
    public String getTime() { return time; }
    public String getTemp() { return temp; }
    public String getConditions() { return conditions; }
    public String getIcon() { return icon; }
}
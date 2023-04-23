package com.example.weatherapp;

import java.io.Serializable;

/*
        datetimeEpoch
        tempmax
        tempmin
        precipprob
        uvindex
        description
        icon
 */

public class OutLook implements Serializable {
    private String date;
    private String tempmax;
    private String tempmin;
    private String precipprob;
    private String uvindex;
    private String description;
    private String morningTemp;
    private String afternoonTemp;
    private String eveningTemp;
    private String nightTemp;
    private String icon;


    public OutLook(String dt, String tmax, String tmin, String pp, String uv, String desc, String mTmp, String aTmp, String eTmp, String nTmp, String img){
        date = dt;
        tempmax = tmax;
        tempmin = tmin;
        precipprob = pp;
        uvindex = uv;
        description = desc;
        morningTemp = mTmp;
        afternoonTemp = aTmp;
        eveningTemp = eTmp;
        nightTemp = nTmp;
        icon = img;
    }

    public String getDate() { return date; }

    public String getMaxTemp() { return tempmax; }

    public String getMinTemp() { return tempmin; }

    public String getPrecipProb() { return precipprob; }

    public String getUV() { return uvindex; }

    public String getDescription() { return description; }

    public String getMorningTemp() { return morningTemp; }

    public String getAfternoonTemp() { return afternoonTemp; }

    public String getEveningTemp() { return eveningTemp; }

    public String getNightTemp() { return nightTemp; }

    public String getIcon() { return icon; }
}

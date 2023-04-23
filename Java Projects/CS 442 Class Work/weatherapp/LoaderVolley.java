package com.example.weatherapp;

import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class LoaderVolley {
    private static final String MAIN_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";
    private static final String GROUP = "?unitGroup=";
    private static final String KEY = "&lang=en&key=GHBBCNPXNNHUU83B7JXHUTR8G";
    private static String addUnit;
    private static long currTime;

    public static void getSourceData(MainActivity mainActivity, String location, String unit){
        String URL = MAIN_URL + location + GROUP + unit + KEY;

        addUnit = "°F";
        if (unit == "metric") {
            addUnit = "°C";
        }

        RequestQueue queue = Volley.newRequestQueue(mainActivity);

        Uri.Builder buildURL = Uri.parse(URL).buildUpon();
        String urlToUse = buildURL.build().toString();

        Response.Listener<JSONObject> listener = response -> handleResults(mainActivity, response.toString());

        Response.ErrorListener error = error1 -> {
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(new String(error1.networkResponse.data));
                handleResults(mainActivity, null);
            } catch (Exception e) {}};

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlToUse, null, listener, error);
        queue.add(jsonObjectRequest);
    }

    private static void handleResults(MainActivity mainActivity, String s) {
        if (s == null){
            mainActivity.downloadFailed();
            return;
        }

        final ArrayList<String> currentList = parseCurrentJSON(s);
        final ArrayList<Hourly> hourlyList = parseHourlyJSON(s);
        final ArrayList<OutLook> outLookList = parseOutLookJSON(s);
        mainActivity.updateData(currentList, hourlyList, outLookList);
    }

    private static ArrayList<String> parseCurrentJSON(String s) {
        ArrayList<String> currentList = new ArrayList<String>();

        try {
            JSONObject objMain = new JSONObject(s);
            JSONObject conditions = objMain.getJSONObject("currentConditions");
            String date = conditions.getString("datetimeEpoch");
            currTime = Long.parseLong(date);
            currentList.add(getTime("full", date));
            String temp = conditions.getString("temp");
            currentList.add(temp);
            String feelslike = conditions.getString("feelslike");
            currentList.add(feelslike);
            String humidity = conditions.getString("humidity");
            currentList.add(humidity);
            String windgust = conditions.getString("windgust");
            currentList.add(windgust);
            String windspeed = conditions.getString("windspeed");
            currentList.add(windspeed);
            String winddir = conditions.getString("winddir");
            currentList.add(windDirection(winddir));
            String visibility = conditions.getString("visibility");
            currentList.add(visibility);
            String cloudcover = conditions.getString("cloudcover");
            currentList.add(cloudcover);
            String uvindex = conditions.getString("uvindex");
            currentList.add(uvindex);
            String cond = conditions.getString("conditions");
            currentList.add(cond);
            String icon = conditions.getString("icon");
            currentList.add(icon);
            String sunrise = conditions.getString("sunriseEpoch");
            currentList.add(getTime("time", sunrise));
            String sunset = conditions.getString("sunsetEpoch");
            currentList.add(getTime("time", sunset));
            String img = conditions.getString("icon");

            JSONArray days = objMain.getJSONArray("days");
            JSONObject oDays = (JSONObject) days.get(0);
            JSONArray hours = oDays.getJSONArray("hours");
            JSONObject mHour = (JSONObject) hours.get(8);
            String morn = mHour.getString("temp");
            currentList.add(morn);
            JSONObject aHour = (JSONObject) hours.get(13);
            String after = aHour.getString("temp");
            currentList.add(after);
            JSONObject eHour = (JSONObject) hours.get(17);
            String even = eHour.getString("temp");
            currentList.add(even);
            JSONObject nHour = (JSONObject) hours.get(23);
            String night = nHour.getString("temp");
            currentList.add(night);

            currentList.add(parseIcon(img));

        } catch (Exception e) {}
        return currentList;
    }

    private static ArrayList<Hourly> parseHourlyJSON(String s) {
        /*
        https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/London,UK/2020-12-15T13:00:00?key=YOUR_API_KEY
        USE THIS OR
        https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/London,UK/1601510400/1609372800?key=YOUR_API_KEY
         */

        ArrayList<Hourly> hList = new ArrayList<Hourly>();

        try {
            // devise entire algorithm because this can go across multiple days!
            JSONObject objMain = new JSONObject(s);
            JSONArray day = objMain.getJSONArray("days");

            int dayAdded = 0;

            while (dayAdded < 48){
                for (int i = 0; i < 3; i++) {
                    JSONObject oDay = (JSONObject) day.get(i);
                    JSONArray hours = oDay.getJSONArray("hours");
                    for (int j = 0; j < hours.length(); j++) {
                        JSONObject hourObj = (JSONObject) hours.get(j);
                        String time = hourObj.getString("datetimeEpoch");
                        if ((Long.parseLong(time) > currTime) && (dayAdded < 48)) {
                            String temp = hourObj.getString("temp");
                            String desc = hourObj.getString("conditions");
                            String img = hourObj.getString("icon");

                            if (i == 0) {
                                hList.add(new Hourly("Today", getTime("time", time), (temp + addUnit), desc, parseIcon(img)));
                            } else {
                                hList.add(new Hourly(getTime("dayOnly", time), getTime("time", time), (temp + addUnit), desc, parseIcon(img)));
                            }

                            dayAdded++;
                        }
                    }
                }
            }
            /*
            for (int i = 0; i < 2; i++) {
                JSONObject oDay = (JSONObject) day.get(i);
                JSONArray hours = oDay.getJSONArray("hours");
                for (int j = 0; j < hours.length()-2; j++) {
                    JSONObject hourObj = (JSONObject) hours.get(j);
                    String time = hourObj.getString("datetimeEpoch");
                    String temp = hourObj.getString("temp");
                    String desc = hourObj.getString("conditions");
                    String img = hourObj.getString("icon");
                    hList.add(new Hourly("--", getTime("time", time), (temp + addUnit), desc, parseIcon(img)));
                }
            }*/
        } catch (Exception e) {}
        return hList;
    }

    private static ArrayList<OutLook> parseOutLookJSON(String s) {
        ArrayList<OutLook> oList = new ArrayList<>();

        try {
            JSONObject objMain = new JSONObject(s);
            JSONArray day = objMain.getJSONArray("days");

            for (int i = 0; i < 15; i++) {
                JSONObject oDay = (JSONObject) day.get(i);
                String img = oDay.getString("icon");
                JSONArray hours = oDay.getJSONArray("hours");
                JSONObject mHour = (JSONObject) hours.get(8);
                String mTmp = mHour.getString("temp");
                JSONObject aHour = (JSONObject) hours.get(13);
                String aTmp = aHour.getString("temp");
                JSONObject eHour = (JSONObject) hours.get(17);
                String eTmp = eHour.getString("temp");
                JSONObject nHour = (JSONObject) hours.get(23);
                String nTmp = nHour.getString("temp");

                String date = oDay.getString("datetimeEpoch");
                String tMax = oDay.getString("tempmax");
                String tMin = oDay.getString("tempmin");
                String perc = oDay.getString("precipprob");
                String uvin = oDay.getString("uvindex");
                String desc = oDay.getString("description");

                oList.add(new OutLook(getTime("dayDate", date), tMax + addUnit, tMin + addUnit, perc, uvin, desc, mTmp + addUnit, aTmp + addUnit, eTmp + addUnit, nTmp + addUnit, parseIcon(img)));
            }

        } catch (Exception e) {}

        return oList;
    }

    private static String getTime(String type, String time) {
        try {
            Date dateTime = new Date(Long.parseLong(time) * 1000);

            SimpleDateFormat fullDate = new SimpleDateFormat("EEE MMM dd h:mm a, yyyy", Locale.getDefault());
            SimpleDateFormat timeOnly = new SimpleDateFormat("h:mm a", Locale.getDefault());
            SimpleDateFormat dayDate = new SimpleDateFormat("EEEE MM/dd", Locale.getDefault());
            SimpleDateFormat dayOnly = new SimpleDateFormat("EEEE", Locale.getDefault());

            String output;
            if (type == "full") {
                output = fullDate.format(dateTime);
            } else if (type == "time") {
                output = timeOnly.format(dateTime);
            } else if (type == "dayOnly") {
                output = dayOnly.format(dateTime);
            } else {
                output = dayDate.format(dateTime);
            }
            return output;

        } catch (Exception e) {
            return "null";
        }
    }

    private static String parseIcon(String icon) {
        String returnIcon = "";

        returnIcon = icon.replace("-", "_");

        return returnIcon;
    }

    private static String windDirection(String degrees) {
        try {
            double degD = Double.parseDouble(degrees);

            if (degD >= 337.5 || degD < 22.5) { return "N";}
            if (degD >= 22.5 && degD < 67.5) { return "NE";}
            if (degD >= 67.5 && degD < 112.5) { return "E";}
            if (degD >= 112.5 && degD < 157.5) { return "SE";}
            if (degD >= 157.5 && degD < 202.5) { return "S";}
            if (degD >= 202.5 && degD < 247.5) { return "SW";}
            if (degD >= 247.5 && degD < 292.5) { return "W";}
            if (degD >= 292.5 && degD < 337.5) { return "NW";}

        } catch (Exception e) {
            return "X";
        }
        return "X";
    }
}

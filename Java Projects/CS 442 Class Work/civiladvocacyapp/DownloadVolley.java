package com.example.civiladvocacyapp;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DownloadVolley {
    private static RequestQueue queue;
    private static final String linkMain = "https://www.googleapis.com/civicinfo/v2/representatives?key=";
    private static final String key = "AIzaSyDypEZMozecIeRFCvUOZ4znKI_LmLBhne4";
    private static final String linkAddress = "&address=";

    public static void getData(MainActivity mainActivity, String address) {
        String URL = linkMain + key + linkAddress + address;

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
        final ArrayList<PoliticianObject> pl = parseJSONPol(s);
        mainActivity.updateData(pl);
    }

    private static ArrayList<PoliticianObject> parseJSONPol(String s) {
        ArrayList<PoliticianObject> returnArr = new ArrayList<>();

        // One for normalized input???

        try {
            JSONObject main = new JSONObject(s);
            JSONArray offices = main.getJSONArray("offices");
            JSONArray officials = main.getJSONArray("officials");

            for (int i = 0; i < offices.length(); i++) {
                JSONObject position = (JSONObject) offices.get(i);
                String title = position.getString("name");
                JSONArray indices = position.getJSONArray("officialIndices");

                for (int j = 0; j < indices.length(); j++) {
                    int index = indices.getInt(j);
                    JSONObject info = (JSONObject) officials.get(index);

                    // Main parse items
                    String name = info.getString("name");

                    String party = "";
                    try {
                        party = info.getString("party");
                    } catch (Exception e) {}

                    String photo = "";
                    try {
                        photo = info.getString("photoUrl");
                    } catch (Exception e) {}

                    // Address parse
                    String fullAddress = "";
                    try {
                        JSONArray address = info.getJSONArray("address");
                        JSONObject addBlock = (JSONObject) address.get(0);

                        try {
                            String line1;
                            try {
                                line1 = addBlock.getString("line1");
                            } catch (Exception e) {
                                line1 = "";
                            }

                            String line2;
                            try {
                                line2 = addBlock.getString("line2");
                            } catch (Exception e) {
                                line2 = "";
                            }

                            String line3;
                            try {
                                line3 = addBlock.getString("line3");
                            } catch (Exception e) {
                                line3 = "";
                            }

                            String city = addBlock.getString("city");
                            String state = addBlock.getString("state");
                            String zip = addBlock.getString("zip");

                            fullAddress = line1 + " " + line2 + " " + line3 + " " + city + ", " + state + " " + zip;
                        } catch (Exception e) {
                        }
                    } catch (Exception e) {}

                    // Contact parse
                    String number = "";
                    try {
                        JSONArray phone = info.getJSONArray("phones");
                        number = phone.getString(0);
                    } catch (Exception e) {}

                    String link = "";
                    try {
                        JSONArray url = info.getJSONArray("urls");
                        link = url.getString(0);
                    } catch (Exception e) {}

                    String emailAdd = "";
                    try {
                        JSONArray email = info.getJSONArray("emails");
                        emailAdd = email.getString(0);
                    } catch (Exception e) {}

                    // PARSE SOCIAL MEDIA
                    JSONArray channels = info.getJSONArray("channels");

                    String twitter = "";
                    String facebook = "";
                    String youtube = "";

                    for (int k = 0; k < channels.length(); k++) {
                        JSONObject channel = (JSONObject) channels.get(k);

                        if (channel.getString("type").equalsIgnoreCase("Facebook")) {
                            facebook = channel.getString("id");
                        }

                        if (channel.getString("type").equalsIgnoreCase("Twitter")) {
                            twitter = channel.getString("id");
                        }

                        if (channel.getString("type").equalsIgnoreCase("YouTUbe")) {
                            youtube = channel.getString("id");
                        }
                    }

                    // SET PHOTO HERE
                    returnArr.add(new PoliticianObject(title, name, party, photo, twitter, facebook, youtube , fullAddress, number, emailAdd, link));
                }
            }
        } catch (Exception e) {}
        return returnArr;
    }
}

package com.example.newaggregator;

import android.net.Uri;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class DownloadVolley {
    private static final String KEY = "apiKey=b39d3253bc6a4e1f82249f3d522bd468";
    private static final String MAIN = "https://newsapi.org/v2/";
    private static final String SOURCE = "sources?";
    private static final String ARTICLE = "top-headlines?sources=";

    public static boolean parseArticle;

    // API KEY b39d3253bc6a4e1f82249f3d522bd468
    // Sources https://newsapi.org/v2/sources?apiKey=_______
    // Specific https://newsapi.org/v2/top-headlines?sources=______&apiKey=______

    public static void getData(MainActivity mainActivity, boolean articleBool, String id) {
        parseArticle = articleBool;

        String URL = MAIN + SOURCE + KEY;
        if (parseArticle) {
            URL = MAIN + ARTICLE + id + "&" + KEY;
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
            } catch (Exception e) {
            }
        };

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlToUse, null, listener, error) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap();
                headers.put("User-Agent", "News-Aoo");
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }

    private static void handleResults(MainActivity mainActivity, String s) {
        if (s == null) {
            mainActivity.downloadFailed();
            return;
        }

        if (parseArticle) {
            final ArrayList<ArticleObject> availArticles = parseArticlesJSON(s);
            mainActivity.updateArticles(availArticles);
        } else {
            final ArrayList<NewsObject> availSources = parseSourcesJSON(s);
            mainActivity.updateSources(availSources);
        }
    }

    private static ArrayList<NewsObject> parseSourcesJSON(String s) {
        ArrayList<NewsObject> sourcesList = new ArrayList<NewsObject>();

        try {
            JSONObject objMain = new JSONObject(s);
            JSONArray sources = objMain.getJSONArray("sources");

            for (int i = 0; i < sources.length(); i++) {
                JSONObject objSource = (JSONObject) sources.get(i);
                String id = objSource.getString("id");
                String nm = objSource.getString("name");
                String ct = objSource.getString("category");
                sourcesList.add(new NewsObject(id, nm, ct));
            }

        } catch (Exception e) {}

        return sourcesList;
    }

    private static ArrayList<ArticleObject> parseArticlesJSON(String s) {
        ArrayList<ArticleObject> articleList = new ArrayList<ArticleObject>();
        int amount = 10;

        try {
            JSONObject objMain = new JSONObject(s);
            JSONArray articles = objMain.getJSONArray("articles");

            if (articles.length() < 10) {
                amount = articles.length();
            }

            for (int i = 0; i < amount; i++) {
                JSONObject objArticle = (JSONObject) articles.get(i);

                String author = "";
                try {
                    if (!(objArticle.getString("author").equalsIgnoreCase("null"))) {
                        author = objArticle.getString("author");
                    }
                } catch (Exception e) {}

                String title = "";
                try {
                    if (!(objArticle.getString("title").equalsIgnoreCase("null"))) {
                        title = objArticle.getString("title");
                    }
                } catch (Exception e) {}

                String description = "";
                try {
                    if (!(objArticle.getString("description").equalsIgnoreCase("null"))) {
                        description = objArticle.getString("description");
                    }
                } catch (Exception e) {}

                String url = "";
                try {
                    if (!(objArticle.getString("url").equalsIgnoreCase("null"))) {
                        url = objArticle.getString("url");
                    }
                } catch (Exception e) {}

                String image = "";
                try {
                    if (!(objArticle.getString("urlToImage").equalsIgnoreCase("null"))) {
                        image = objArticle.getString("urlToImage");
                    }
                } catch (Exception e) {}

                String published = "";
                try {
                    if (!(objArticle.getString("publishedAt").equalsIgnoreCase("null"))) {
                        published = objArticle.getString("publishedAt");
                    }
                } catch (Exception e) {}

                articleList.add(new ArticleObject(author, title, description, url, published, image));
            }

        } catch (Exception e) {}

        return articleList;
    }
}

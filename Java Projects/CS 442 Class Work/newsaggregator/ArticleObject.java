package com.example.newaggregator;

import java.io.Serializable;

public class ArticleObject implements Serializable {
   private String author;
   private String title;
   private String description;
   private String url;
   private String published;
   private String image;

   ArticleObject (String a, String t, String d, String u, String p, String i) {
      author = a;
      title = t;
      description = d;
      url = u;
      published = p;
      image = i;
   }

   public String getAuthor() { return author; }
   public String getTitle() { return title; }
   public String getDescription() { return description; }
   public String getUrl() { return url; }
   public String getPublished() { return published; }
   public String getImage() { return image; }
}

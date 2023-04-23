package com.example.newaggregator;

import java.io.Serializable;

public class NewsObject implements Serializable {
    private String id;
    private String name;
    private String category;

    public NewsObject(String i, String n, String c) {
        id = i;
        name = n;
        category = c;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
}

package com.example.androidnotes;

import android.util.JsonWriter;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.io.StringWriter;

public class Note implements Serializable {
    private String label;
    private String date;
    private String description;

    // Structure
    public Note(String label, String date, String description){
        this.label = label;
        this.date = date;
        this.description = description;
    }

    // Setters
    public void setLabel(String label) {
        this.label = label;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getters
    public String getLabel() {
        return label;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }
}

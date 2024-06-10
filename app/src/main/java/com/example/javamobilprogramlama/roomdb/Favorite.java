package com.example.javamobilprogramlama.roomdb;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite")
public class Favorite {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String url;
    private String title;
    private String date;

    public Favorite(String url, String title, String date) {
        this.url = url;
        this.title = title;
        this.date = date;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
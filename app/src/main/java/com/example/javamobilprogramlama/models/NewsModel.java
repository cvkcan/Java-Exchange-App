package com.example.javamobilprogramlama.models;

public class NewsModel {
    private String title;
    private String dateAndWriter;
    private String url;
    private String language;

    public String getTitle(){
        return title;
    }
    public String getDateAndWriter(){
        return dateAndWriter;
    }
    public String getUrl(){
        return url;
    }
    public String getLanguage(){
        return language;
    }

    public NewsModel(String title, String dateAndWriter, String url, String language) {
        this.title = title;
        this.dateAndWriter = dateAndWriter;
        this.url = url;
        this.language = language;
    }
}

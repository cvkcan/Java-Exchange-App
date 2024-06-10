package com.example.javamobilprogramlama.models;

public class NewsDetail {
    private String title;
    private String content;
    private String datetime;

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDatetime() {
        return datetime;
    }
    public NewsDetail(String title, String content, String datetime) {
        this.title = title;
        this.content = content;
        this.datetime = datetime;
    }
}

package com.example.laba3;

public class Notification {
    private int id;
    private String title;
    private String message;
    private String date;

    public Notification(int id, String title, String message, String date) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }
}


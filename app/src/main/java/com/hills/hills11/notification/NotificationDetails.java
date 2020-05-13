package com.hills.hills11.notification;

public class NotificationDetails {
    private String title, time, description, icon, url;

    public NotificationDetails() {
    }
    public NotificationDetails(String title , String time , String description , String icon) {
        this.title = title;
        this.time = time;
        this.description = description;
        this.icon = icon;
    }

    public NotificationDetails(String title , String time , String description , String icon, String url) {
        this.title = title;
        this.time = time;
        this.description = description;
        this.icon = icon;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

}

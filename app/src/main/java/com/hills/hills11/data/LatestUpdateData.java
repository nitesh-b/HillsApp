package com.hills.hills11.data;

public class LatestUpdateData {
    private String image;
    private String title;
    private String description;
    private String date;
    private String link;

    public LatestUpdateData() {
    }

    public LatestUpdateData(String image , String title , String description , String date , String link) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.date = date;
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getLink() {
        return link;
    }
}

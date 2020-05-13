package com.hills.hills11.data;

public class NewsEventDetails {
    private String decription, image, imgName, link, title;

    public NewsEventDetails() {
    }

    public NewsEventDetails(String decription , String image , String imgName , String link , String title) {
        this.decription = decription;
        this.image = image;
        this.imgName = imgName;
        this.link = link;
        this.title = title;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

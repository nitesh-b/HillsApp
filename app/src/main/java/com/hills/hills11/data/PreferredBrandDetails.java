package com.hills.hills11.data;

public class PreferredBrandDetails {
    private String logo;
    private String link;

    public PreferredBrandDetails(String logo , String link) {
        this.logo = logo;
        this.link = link;
    }

    public PreferredBrandDetails() {
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


}

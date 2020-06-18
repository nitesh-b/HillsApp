package com.hills.hills11.data;


import com.google.android.gms.maps.model.LatLng;

import java.util.Map;

public class LocationDetailData {
    private LatLng latLng;
    private String name;
    private String address;
    private String phone;
    private Map<String, String > openingHours;

    public LocationDetailData(LatLng latLng , String name , String address , String phone , Map<String, String> openingHours) {
        this.latLng = latLng;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.openingHours = openingHours;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public Map<String, String> getOpeningHours() {
        return openingHours;
    }
}

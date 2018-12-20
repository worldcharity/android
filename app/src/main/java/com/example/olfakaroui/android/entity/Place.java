package com.example.olfakaroui.android.entity;

import com.google.gson.annotations.SerializedName;

public class Place {

    @SerializedName("id")
    private int id;
    @SerializedName("longitude")
    private double longitude;
    @SerializedName("latitude")
    private  double latitude;

    public Place() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}

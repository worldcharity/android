package com.example.olfakaroui.android.entity;

import com.google.gson.annotations.SerializedName;

public class Photo {

    @SerializedName("id")
    private int id;
    @SerializedName("url")
    private String photo;

    public Photo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}

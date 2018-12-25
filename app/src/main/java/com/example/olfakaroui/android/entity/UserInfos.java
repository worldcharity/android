package com.example.olfakaroui.android.entity;

import com.google.gson.annotations.SerializedName;

public class UserInfos {
    @SerializedName("rating")
    private Float rating;
    @SerializedName("collabs")
    private int collaborations;

    public UserInfos() {
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public int getCollaborations() {
        return collaborations;
    }

    public void setCollaborations(int collaborations) {
        this.collaborations = collaborations;
    }
}

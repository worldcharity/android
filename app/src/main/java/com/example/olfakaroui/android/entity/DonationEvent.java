package com.example.olfakaroui.android.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DonationEvent implements Serializable {


    @SerializedName("goal")
    private float goal;


    public DonationEvent() {
    }

     public float getGoal() {
        return goal;
    }

    public void setGoal(float goal) {
        this.goal = goal;
    }


}

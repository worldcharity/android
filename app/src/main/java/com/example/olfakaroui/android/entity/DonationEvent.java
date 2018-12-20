package com.example.olfakaroui.android.entity;

import com.google.gson.annotations.SerializedName;

public class DonationEvent {

    private DonationType donation;
    @SerializedName("goal")
    private float goal;
    private Event event;


    public DonationEvent() {
    }

    public DonationType getDonation() {
        return donation;
    }

    public void setDonation(DonationType donation) {
        this.donation = donation;
    }

    public float getGoal() {
        return goal;
    }

    public void setGoal(float goal) {
        this.goal = goal;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}

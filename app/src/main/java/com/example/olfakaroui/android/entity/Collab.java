package com.example.olfakaroui.android.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Collab implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("body")
    private String body;
    @SerializedName("amount")
    private double amount;
    @SerializedName("state")
    private int state;
    @SerializedName("createdAt")
    private Date collab_date;
    @SerializedName("user")
    private User collab_by;
    @SerializedName("donationtype")
    private DonationType collab_type;
    @SerializedName("event")
    private Event event;

    public Collab() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getCollab_date() {
        return collab_date;
    }

    public void setCollab_date(Date collab_date) {
        this.collab_date = collab_date;
    }

    public User getCollab_by() {
        return collab_by;
    }

    public void setCollab_by(User collab_by) {
        this.collab_by = collab_by;
    }

    public DonationType getCollab_type() {
        return collab_type;
    }

    public void setCollab_type(DonationType collab_type) {
        this.collab_type = collab_type;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}

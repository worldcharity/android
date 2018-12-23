package com.example.olfakaroui.android.entity;

import java.io.Serializable;

public class Fav implements Serializable {

    private User faved_by;
    private Event event;

    public Fav() {
    }

    public User getFaved_by() {
        return faved_by;
    }

    public void setFaved_by(User faved_by) {
        this.faved_by = faved_by;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}

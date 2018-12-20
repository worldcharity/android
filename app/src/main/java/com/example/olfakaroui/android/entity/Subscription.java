package com.example.olfakaroui.android.entity;

public class Subscription {

    private User subscriber;
    private User subscribedTo;

    public Subscription() {
    }

    public User getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(User subscriber) {
        this.subscriber = subscriber;
    }

    public User getSubscribedTo() {
        return subscribedTo;
    }

    public void setSubscribedTo(User subscribedTo) {
        this.subscribedTo = subscribedTo;
    }
}

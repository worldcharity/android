package com.example.olfakaroui.android.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class Comment {

    @SerializedName("id")
    private int id;
    @SerializedName("body")
    private String body;
    @SerializedName("createdAt")
    private Date posting_date;
    private Event event;
    private Post post;
    @SerializedName("User")
    private User posted_by;
    @SerializedName("Votes")
    private ArrayList<Vote> votes = new ArrayList<>();
    @SerializedName("state")
    private int state;

    public Comment() {
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

    public Date getPosting_date() {
        return posting_date;
    }

    public void setPosting_date(Date posting_date) {
        this.posting_date = posting_date;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getPosted_by() {
        return posted_by;
    }

    public void setPosted_by(User posted_by) {
        this.posted_by = posted_by;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public ArrayList<Vote> getVotes() {
        return votes;
    }

    public void setVotes(ArrayList<Vote> votes) {
        this.votes = votes;
    }

}

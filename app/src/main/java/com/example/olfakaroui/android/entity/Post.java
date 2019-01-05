package com.example.olfakaroui.android.entity;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Post implements Comparable<Post>, Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("body")
    private String body;
    @SerializedName("createdAt")
    private Date postingDate;
    @SerializedName("user")
    private User user;
    @SerializedName("cause")
    private Cause cause;
    @SerializedName("Comments")
    private ArrayList<Comment> comments = new ArrayList<>();
    @SerializedName("Votes")
    private ArrayList<Vote> votes = new ArrayList<>();


    public Post() {
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

    public Date getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(Date postingDate) {
        this.postingDate = postingDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cause getCause() {
        return cause;
    }

    public void setCause(Cause cause) {
        this.cause = cause;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public ArrayList<Vote> getVotes() {
        return votes;
    }

    public void setVotes(ArrayList<Vote> votes) {
        this.votes = votes;
    }

    @Override
    public int compareTo(@NonNull Post post) {
        int ret = post.getVotes().size()-this.getVotes().size();
        return ret;
    }
}


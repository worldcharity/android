package com.example.olfakaroui.android.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class Vote implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("type")
    private String type;
    private Comment comment;
    private Event event;
    private Post post;
    @SerializedName("user")
    private User voted_by;
    @SerializedName("state")
    private int state;

    public Vote() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getVoted_by() {
        return voted_by;
    }

    public void setVoted_by(User voted_by) {
        this.voted_by = voted_by;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof Vote))
            return false;
        Vote other = (Vote) obj;
        return id == other.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}

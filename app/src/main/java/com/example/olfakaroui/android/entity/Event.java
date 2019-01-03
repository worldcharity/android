package com.example.olfakaroui.android.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.List;

public class Event implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("infoline")
    private String infoline;
    @SerializedName("description")
    private String description;
    @SerializedName("starting_date")
    private Date startingDate;
    @SerializedName("ending_date")
    private Date endingDate;
    @SerializedName("createdAt")
    private Date creationDate;
    @SerializedName("user")
    private User postedBy;
    @SerializedName("cause")
    private Cause cause;
    @SerializedName("Photos")
    private List<Photo> photos;
    @SerializedName("Votes")
    private List<Vote> votes;
    @SerializedName("Comments")
    private List<Comment> comments;
    @SerializedName("Types")
    private List<DonationType> donationEvents;
    @SerializedName("type")
    private String type;
    @SerializedName("longitude")
    private double longitude;
    @SerializedName("latitude")
    private  double latitude;
    @SerializedName("FavBy")
    private List<User> favBy;

    public Event() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public Date getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(Date endingDate) {
        this.endingDate = endingDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public User getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(User postedBy) {
        this.postedBy = postedBy;
    }

    public Cause getCause() {
        return cause;
    }

    public void setCause(Cause cause) {
        this.cause = cause;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getInfoline() {
        return infoline;
    }

    public void setInfoline(String infoline) {
        this.infoline = infoline;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public List<User> getFavBy() {
        return favBy;
    }

    public void setFavBy(List<User> favBy) {
        this.favBy = favBy;
    }

    public List<DonationType> getDonationEvents() {
        return donationEvents;
    }

    public void setDonationEvents(List<DonationType> donationEvents) {
        this.donationEvents = donationEvents;
    }
}

package com.example.olfakaroui.android.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class User implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("social_id")
    private String socialId;
    @SerializedName("createdAt")
    private Date creationDate;
    @SerializedName("role")
    private String role;
    @SerializedName("social_platform")
    private String socialPlatform;
    @SerializedName("name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("photo")
    private String photo;
    @SerializedName("description")
    private String description;
    @SerializedName("longitude")
    private double longitude;
    @SerializedName("latitude")
    private double latitude;
    @SerializedName("confirmation_photo")
    private String confirmationPhoto;
    @SerializedName("Prefrences")
    private List<Cause> prefrences = new ArrayList<>();
    @SerializedName("Users")
    private List<User> following = new ArrayList<>();
    @SerializedName("Subs")
    private List<User> followers = new ArrayList<>();
    @SerializedName("Events")
    private List<Event> events = new ArrayList<>();



    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Cause> getPrefrences() {
        return prefrences;
    }

    public void setPrefrences(ArrayList<Cause> prefrences) {
        this.prefrences = prefrences;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getrole() {
        return role;
    }

    public void setrole(String role) {
        this.role = role;
    }

    public String getSocialPlatform() {
        return socialPlatform;
    }

    public void setSocialPlatform(String socialPlatform) {
        this.socialPlatform = socialPlatform;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getConfirmationPhoto() {
        return confirmationPhoto;
    }

    public void setConfirmationPhoto(String confirmationPhoto) {
        this.confirmationPhoto = confirmationPhoto;
    }
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPrefrences(List<Cause> prefrences) {
        this.prefrences = prefrences;
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId() == user.getId();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }
}

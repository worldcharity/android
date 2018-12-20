package com.example.olfakaroui.android.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class User {

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
    private Float rating;



    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
}

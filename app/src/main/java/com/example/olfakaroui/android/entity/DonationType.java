package com.example.olfakaroui.android.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class DonationType implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("type")
    private String name;
    @SerializedName("quota")
    private float quota;
    @SerializedName("donationevent")
    private DonationEvent donationevent;


    public DonationType() {
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

    public float getQuota() {
        return quota;
    }

    public void setQuota(float quota) {
        this.quota = quota;
    }

    public DonationEvent getDonationevent() {
        return donationevent;
    }

    public void setDonationevent(DonationEvent donationevent) {
        this.donationevent = donationevent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DonationType that = (DonationType) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}

package com.swift.akc.network.data;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class HarvestForcastingVO {
    @Expose
    @SerializedName("id")
    private int forcastId;

    @Expose
    @SerializedName("area")
    private String forcastArea;

    @Expose
    @SerializedName("crop_showing_date")
    private String cropDate;

    @Expose
    @SerializedName("date")
    private String date;

    @Expose
    @SerializedName("farm_id")
    private String farm;

    @Expose
    @SerializedName("plant_id")
    private String plant;

    @Expose
    @SerializedName("seeds")
    private String seeds;

    @Expose
    @SerializedName("time")
    private String time;

    public int getForcastId() {
        return forcastId;
    }

    public void setForcastId(int forcastId) {
        this.forcastId = forcastId;
    }

    public String getForcastArea() {
        return forcastArea;
    }

    public void setForcastArea(String forcastArea) {
        this.forcastArea = forcastArea;
    }

    public String getCropDate() {
        return cropDate;
    }

    public void setCropDate(String cropDate) {
        this.cropDate = cropDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFarm() {
        return farm;
    }

    public void setFarm(String farm) {
        this.farm = farm;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getSeeds() {
        return seeds;
    }

    public void setSeeds(String seeds) {
        this.seeds = seeds;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

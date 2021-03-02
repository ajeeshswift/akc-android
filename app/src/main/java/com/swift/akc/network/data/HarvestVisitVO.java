package com.swift.akc.network.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HarvestVisitVO {

    @Expose
    @SerializedName("id")
    private int harvestVisitId;

    @Expose
    @SerializedName("sapQuantity")
    private String sapQuantity;

    @Expose
    @SerializedName("sowingDate")
    private String sowingDate;

    @Expose
    @SerializedName("harvestDate")
    private String harvestDate;

    @Expose
    @SerializedName("harvestQuantity")
    private String harvestQuantity;

    @Expose
    @SerializedName("ownUseQuantity")
    private String ownUseQuantity;

    @Expose
    @SerializedName("soldQuantity")
    private String soldQuantity;

    @Expose
    @SerializedName("soldRate")
    private String soldRate;

    @Expose
    @SerializedName("totalIncome")
    private String totalIncome;

    public int getHarvestVisitId() {
        return harvestVisitId;
    }

    public void setHarvestVisitId(int harvestVisitId) {
        this.harvestVisitId = harvestVisitId;
    }

    public String getSapQuantity() {
        return sapQuantity;
    }

    public void setSapQuantity(String sapQuantity) {
        this.sapQuantity = sapQuantity;
    }

    public String getSowingDate() {
        return sowingDate;
    }

    public void setSowingDate(String sowingDate) {
        this.sowingDate = sowingDate;
    }

    public String getHarvestDate() {
        return harvestDate;
    }

    public void setHarvestDate(String harvestDate) {
        this.harvestDate = harvestDate;
    }

    public String getHarvestQuantity() {
        return harvestQuantity;
    }

    public void setHarvestQuantity(String harvestQuantity) {
        this.harvestQuantity = harvestQuantity;
    }

    public String getOwnUseQuantity() {
        return ownUseQuantity;
    }

    public void setOwnUseQuantity(String ownUseQuantity) {
        this.ownUseQuantity = ownUseQuantity;
    }

    public String getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(String soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public String getSoldRate() {
        return soldRate;
    }

    public void setSoldRate(String soldRate) {
        this.soldRate = soldRate;
    }

    public String getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(String totalIncome) {
        this.totalIncome = totalIncome;
    }
}

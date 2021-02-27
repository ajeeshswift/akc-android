package com.swift.akc.network.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HarvestVisitVO {

    @Expose
    @SerializedName("id")
    private int harvestVisitId;

    @Expose
    @SerializedName("sapQuantity")
    private String sapQuantity;

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
}

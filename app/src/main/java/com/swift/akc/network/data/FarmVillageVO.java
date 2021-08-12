package com.swift.akc.network.data;

public class FarmVillageVO {

    private int farmDetailId;
    private int villageId;
    private String villageName;
    private int farmId;
    private String farmName;
    private String farmNo;


    public int farmDetailId() {
        return farmDetailId;
    }

    public void farmDetailId(int farmDetailId) {
        this.farmDetailId = farmDetailId;
    }

    public int getVillageId() {
        return villageId;
    }

    public void setVillageId(int villageId) {
        this.villageId = villageId;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public int getFarmId() {
        return farmId;
    }

    public void setFarmId(int farmId) {
        this.farmId = farmId;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public String getFarmNo() {
        return farmNo;
    }

    public void setFarmNo(String farmNo) {
        this.farmNo = farmNo;
    }



}

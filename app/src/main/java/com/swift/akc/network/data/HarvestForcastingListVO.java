package com.swift.akc.network.data;

import java.util.List;

public class HarvestForcastingListVO extends AbstractListResponseVO {
    private List<HarvestForcastingVO> data;

    public List<HarvestForcastingVO> getData() {
        return data;
    }

    public void setData(List<HarvestForcastingVO> data) {
        this.data = data;
    }
}

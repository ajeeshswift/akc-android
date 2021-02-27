package com.swift.akc.network.data;

import java.util.List;

public class HarvestVisitListVO extends AbstractListResponseVO {

    private List<HarvestVisitVO> data;

    public List<HarvestVisitVO> getData() {
        return data;
    }

    public void setData(List<HarvestVisitVO> data) {
        this.data = data;
    }
}

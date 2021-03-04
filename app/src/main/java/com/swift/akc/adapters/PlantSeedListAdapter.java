package com.swift.akc.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.swift.akc.R;
import com.swift.akc.network.data.PlantSeedVO;

import java.util.ArrayList;
import java.util.List;

public class PlantSeedListAdapter extends ArrayAdapter<PlantSeedVO> {

    private List<PlantSeedVO> plantSeedVOList = new ArrayList<>();

    private Context mContext;

    private int resourceLayout;

    public PlantSeedListAdapter(Context context, int resource, List<PlantSeedVO> plantSeedVOList) {
        super(context, resource, plantSeedVOList);
        this.plantSeedVOList = plantSeedVOList;
        this.mContext = context;
        this.resourceLayout = resource;
    }

    public void refresh(List<PlantSeedVO> plantSeedVOList) {
        this.plantSeedVOList = plantSeedVOList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return plantSeedVOList.size();
    }

    @Override
    public PlantSeedVO getItem(int position) {
        return plantSeedVOList.get(position);
    }

    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(resourceLayout, parent, false);
        }
        TextView strName = view.findViewById(R.id.autoComplete);
        strName.setTag(getItem(position));
        strName.setText(getItem(position).getFloraName());
        return view;
    }
}

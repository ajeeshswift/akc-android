package com.swift.akc.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.swift.akc.R;
import com.swift.akc.network.data.HarvestForcastingVO;

import java.util.ArrayList;
import java.util.List;

public class HarvestForcastAdapter extends RecyclerView.Adapter<HarvestForcastAdapter.HarvestForcastingViewHolder>{
    public static final String TAG = "MyOrderStoreListAdapter";

    private Activity mContext;

    private List<HarvestForcastingVO> harvestForcastingVOList = new ArrayList<>();

    public HarvestForcastAdapter(Activity context) {
        this.mContext = context;
    }

    public void refresh(List<HarvestForcastingVO> harvestForcastingVOList) {
        this.harvestForcastingVOList = harvestForcastingVOList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HarvestForcastAdapter.HarvestForcastingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_harvest_entry, viewGroup, false);
        return new HarvestForcastAdapter.HarvestForcastingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HarvestForcastAdapter.HarvestForcastingViewHolder holder, int position) {
        HarvestForcastingVO harvestForcastingVO = harvestForcastingVOList.get(position);
        holder.itemEntry.setText(harvestForcastingVO.getForcastArea());
        holder.itemEntry.setText(harvestForcastingVO.getCropDate());
        holder.itemEntry.setText(harvestForcastingVO.getDate());
    }

    @Override
    public int getItemCount() {
        return harvestForcastingVOList.size();
    }

    public static class HarvestForcastingViewHolder extends RecyclerView.ViewHolder {
        TextView itemEntry;
        public HarvestForcastingViewHolder(View view) {
            super(view);
            itemEntry = view.findViewById(R.id.item_entry);
        }
    }
}

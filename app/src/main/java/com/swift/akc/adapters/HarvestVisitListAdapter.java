package com.swift.akc.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.swift.akc.R;
import com.swift.akc.network.data.HarvestVisitVO;

import java.util.ArrayList;
import java.util.List;

public class HarvestVisitListAdapter extends RecyclerView.Adapter<HarvestVisitListAdapter.HarvestEntryViewHolder> {

    public static final String TAG = "MyOrderStoreListAdapter";

    private Activity mContext;

    private List<HarvestVisitVO> harvestVisitVOList = new ArrayList<>();

    public HarvestVisitListAdapter(Activity context) {
        this.mContext = context;
    }

    public void refresh(List<HarvestVisitVO> harvestVisitVOList) {
        this.harvestVisitVOList = harvestVisitVOList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HarvestVisitListAdapter.HarvestEntryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_harvest_list, viewGroup, false);
        return new HarvestVisitListAdapter.HarvestEntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HarvestVisitListAdapter.HarvestEntryViewHolder holder, int position) {
        HarvestVisitVO harvestVisitVO = harvestVisitVOList.get(position);
        holder.sapQuantity.setText(harvestVisitVO.getSapQuantity());
        holder.sowingDate.setText(harvestVisitVO.getSowingDate());
        holder.harvestDate.setText(harvestVisitVO.getHarvestDate());
        holder.harvestQuantity.setText(harvestVisitVO.getHarvestQuantity());
        holder.ownUseQuantity.setText(harvestVisitVO.getOwnUseQuantity());
        holder.soldQuantity.setText(harvestVisitVO.getSoldQuantity());
        holder.soldRate.setText(harvestVisitVO.getSoldRate());
        holder.totalIncome.setText(harvestVisitVO.getTotalIncome());
    }

    @Override
    public int getItemCount() {
        return harvestVisitVOList.size();
    }

    public static class HarvestEntryViewHolder extends RecyclerView.ViewHolder {
        TextView sapQuantity,sowingDate,harvestDate,harvestQuantity,ownUseQuantity,soldQuantity,soldRate,totalIncome;
        public HarvestEntryViewHolder(View view) {
            super(view);
            sapQuantity = view.findViewById(R.id.sapQuantity);
            sowingDate = view.findViewById(R.id.sowingDate);
            harvestDate = view.findViewById(R.id.harvestDate);
            harvestQuantity = view.findViewById(R.id.harvestQuantity);
            ownUseQuantity = view.findViewById(R.id.ownUseQuantity);
            soldQuantity = view.findViewById(R.id.soldQuantity);
            soldRate = view.findViewById(R.id.soldRate);
            totalIncome = view.findViewById(R.id.totalIncome);
        }
    }
}

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

public class HarvestVisitAdapter extends RecyclerView.Adapter<HarvestVisitAdapter.HarvestEntryViewHolder> {

    public static final String TAG = "MyOrderStoreListAdapter";

    private Activity mContext;

    private List<HarvestVisitVO> harvestVisitVOList = new ArrayList<>();

    public HarvestVisitAdapter(Activity context) {
        this.mContext = context;
    }

    public void refresh(List<HarvestVisitVO> harvestVisitVOList) {
        this.harvestVisitVOList = harvestVisitVOList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HarvestVisitAdapter.HarvestEntryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_harvest_entry, viewGroup, false);
        return new HarvestVisitAdapter.HarvestEntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HarvestVisitAdapter.HarvestEntryViewHolder holder, int position) {
        HarvestVisitVO harvestVisitVO = harvestVisitVOList.get(position);
        holder.itemEntry.setText(harvestVisitVO.getSapQuantity());
    }

    @Override
    public int getItemCount() {
        return harvestVisitVOList.size();
    }

    public static class HarvestEntryViewHolder extends RecyclerView.ViewHolder {
        TextView itemEntry;
        public HarvestEntryViewHolder(View view) {
            super(view);
            itemEntry = view.findViewById(R.id.item_entry);
        }
    }
}

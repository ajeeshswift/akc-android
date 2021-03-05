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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HarvestForecastingListAdapter extends RecyclerView.Adapter<HarvestForecastingListAdapter.HarvestForcastingViewHolder>{
    public static final String TAG = "MyOrderStoreListAdapter";

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private Activity mContext;

    private List<HarvestForcastingVO> harvestForcastingVOList = new ArrayList<>();

    public HarvestForecastingListAdapter(Activity context) {
        this.mContext = context;
    }

    public void refresh(List<HarvestForcastingVO> harvestForcastingVOList) {
        this.harvestForcastingVOList = harvestForcastingVOList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HarvestForcastingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_harvest_focasting_list, viewGroup, false);
        return new HarvestForcastingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HarvestForcastingViewHolder holder, int position) {
        HarvestForcastingVO harvestForcastingVO = harvestForcastingVOList.get(position);

        holder.area.setText(harvestForcastingVO.getForcastArea());
        holder.cropDate.setText(harvestForcastingVO.getCropDate());
        //holder.date.setText(harvestForcastingVO.getDate());
        holder.farm.setText(harvestForcastingVO.getFarm());
        holder.plant.setText(harvestForcastingVO.getPlant());
        holder.seeds.setText(harvestForcastingVO.getSeeds());
        //holder.time.setText(harvestForcastingVO.getTime());
    }

    @Override
    public int getItemCount() {
        return harvestForcastingVOList.size();
    }

    public static class HarvestForcastingViewHolder extends RecyclerView.ViewHolder {
        TextView area,cropDate,date,farm,plant,seeds,time;
        public HarvestForcastingViewHolder(View view) {
            super(view);
            area = view.findViewById(R.id.area);
            cropDate = view.findViewById(R.id.cropDate);
            //date = view.findViewById(R.id.date);
            farm = view.findViewById(R.id.farm);
            plant = view.findViewById(R.id.plant);
            seeds = view.findViewById(R.id.seeds);
            //time = view.findViewById(R.id.time);
        }
    }
}

package com.swift.akc.adapters;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.swift.akc.R;
import com.swift.akc.network.data.HarvestVisitVO;

import java.util.ArrayList;
import java.util.List;

public class HarvestVisitListAdapterSql  extends RecyclerView.Adapter<HarvestVisitListAdapterSql.HarvestEntryViewHolder> {

    public static final String TAG = "MyOrderStoreListAdapter";

    private Activity mContext;

    ArrayList<Integer> myflorastid              = new ArrayList<>();
    ArrayList<String>  myfloraId                = new ArrayList<>();
    ArrayList<String> mystrsowingDate           = new ArrayList<>();
    ArrayList<String> mystrsapQuantity          = new ArrayList<>();
    ArrayList<String> mystrsoldQuantity         = new ArrayList<>();
    ArrayList<String> mystrharvestDate          = new ArrayList<>();
    ArrayList<String> mystrharvestQuantity      = new ArrayList<>();
    ArrayList<String> mystrownUseQuantity       = new ArrayList<>();
    ArrayList<String> mystrsoldRate             = new ArrayList<>();
    ArrayList<String> mystrtotalIncome          = new ArrayList<>();
    ArrayList<String> myStatus                  = new ArrayList<>();
    ArrayList<String> myFarmerName              = new ArrayList<>();
    ArrayList<String> myVillageName             = new ArrayList<>();
    ArrayList<String> myplanseed                = new ArrayList<>();

    public HarvestVisitListAdapterSql(FragmentActivity activity,
                                      ArrayList<String> mystrsowingDate,
                                      ArrayList<String> mystrsapQuantity,
                                      ArrayList<String> mystrsoldQuantity,
                                      ArrayList<String> mystrharvestDate,
                                      ArrayList<String> mystrharvestQuantity,
                                      ArrayList<String> mystrownUseQuantity,
                                      ArrayList<String> mystrsoldRate,
                                      ArrayList<String> mystrtotalIncome,
                                      ArrayList<String> myStatus, ArrayList<String> myFarmerName,
                                      ArrayList<String> myVillageName, ArrayList<String> myplanseed) {

        this.mContext               = activity;
        this.mystrsowingDate        = mystrsowingDate;
        this.mystrsapQuantity       = mystrsapQuantity;
        this.mystrsoldQuantity      = mystrsoldQuantity;
        this.mystrharvestDate       = mystrharvestDate;
        this.mystrharvestQuantity   = mystrharvestQuantity;
        this.mystrownUseQuantity    = mystrownUseQuantity;
        this.mystrsoldRate          = mystrsoldRate;
        this.mystrtotalIncome       = mystrtotalIncome;
        this.myStatus               = myStatus;
        this.myFarmerName           = myFarmerName;
        this.myVillageName          = myVillageName;
        this.myplanseed             = myplanseed;



    }

    @NonNull
    @Override
    public HarvestVisitListAdapterSql.HarvestEntryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_harvest_list, viewGroup, false);
        return new HarvestVisitListAdapterSql.HarvestEntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HarvestEntryViewHolder holder, int position) {
        holder.farmerName.setText(myFarmerName.get(position));
        holder.villName.setText(myVillageName.get(position));
        holder.plantORseed.setText(myplanseed.get(position));
        holder.sapQuantity.setText(mystrsapQuantity.get(position));
        holder.sowingDate.setText(mystrsowingDate.get(position));
        holder.harvestDate.setText(mystrharvestDate.get(position));
        holder.harvestQuantity.setText(mystrharvestQuantity.get(position));
        holder.ownUseQuantity.setText(mystrownUseQuantity.get(position));
        holder.soldQuantity.setText(mystrsoldQuantity.get(position));
        holder.soldRate.setText(mystrsoldRate.get(position));
        holder.totalIncome.setText(mystrtotalIncome.get(position));

        if(myStatus.get(position).equals("0")){
            holder.imgStatus.setImageResource(R.drawable.red_dot);
        } else{
            holder.imgStatus.setImageResource(R.drawable.green_dot);

        }
    }



    @Override
    public int getItemCount() {
        return mystrsapQuantity.size();
    }

    public static class HarvestEntryViewHolder extends RecyclerView.ViewHolder {
        TextView farmerName,villName,plantORseed,sapQuantity,sowingDate,harvestDate,
                harvestQuantity,ownUseQuantity,soldQuantity,soldRate,totalIncome;

        ImageView imgStatus;
        public HarvestEntryViewHolder(View view) {
            super(view);
            farmerName = view.findViewById(R.id.farm);
            villName = view.findViewById(R.id.villName);
            plantORseed = view.findViewById(R.id.plantORseed);
            sapQuantity = view.findViewById(R.id.sapQuantity);
            sowingDate = view.findViewById(R.id.sowingDate);
            harvestDate = view.findViewById(R.id.harvestDate);
            harvestQuantity = view.findViewById(R.id.harvestQuantity);
            ownUseQuantity = view.findViewById(R.id.ownUseQuantity);
            soldQuantity = view.findViewById(R.id.soldQuantity);
            soldRate = view.findViewById(R.id.soldRate);
            totalIncome = view.findViewById(R.id.totalIncome);
            imgStatus = view.findViewById(R.id.img_status);



        }
    }
}

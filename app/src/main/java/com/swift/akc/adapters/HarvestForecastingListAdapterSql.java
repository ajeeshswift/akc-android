package com.swift.akc.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.swift.akc.R;
import com.swift.akc.network.data.HarvestForcastingVO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HarvestForecastingListAdapterSql extends RecyclerView.Adapter<HarvestForecastingListAdapterSql.HarvestForcastingViewHolder>{
    public static final String TAG = "MyOrderStoreListAdapter";

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private Activity mContext;

    private List<HarvestForcastingVO> harvestForcastingVOList = new ArrayList<>();
    ArrayList<Integer> myforcastid = new ArrayList<>();
    ArrayList<String> myfarm = new ArrayList<>();
    ArrayList<String> myforcastarea = new ArrayList<>();
    ArrayList<String> myplant = new ArrayList<>();
    ArrayList<String> mydate = new ArrayList<>();
    ArrayList<String> mytime = new ArrayList<>();
    ArrayList<String> mycropDate = new ArrayList<>();
    ArrayList<String> myseeds = new ArrayList<>();
    ArrayList<String> mystatus = new ArrayList<>();


    public HarvestForecastingListAdapterSql(FragmentActivity activity,
                                            ArrayList<Integer> myforcastid,
                                            ArrayList<String> myfarm, ArrayList<String> myforcastarea,
                                            ArrayList<String> myplant, ArrayList<String> mydate,
                                            ArrayList<String> mytime, ArrayList<String> mycropDate,
                                            ArrayList<String> myseeds,
                                            ArrayList<String> mystatus) {
        this.mContext = activity;
        this.myforcastid = myforcastid;
        this.myfarm = myfarm;
        this.myforcastarea = myforcastarea;
        this.myplant = myplant;
        this.mydate = mydate;
        this.mytime = mytime;
        this.mycropDate = mycropDate;
        this.myseeds = myseeds;
        this.mystatus = mystatus;


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
      //  HarvestForcastingVO harvestForcastingVO = harvestForcastingVOList.get(position);

        holder.area.setText(myforcastarea.get(position));
        holder.cropDate.setText(mycropDate.get(position));
        holder.plant.setText(myplant.get(position));
        holder.seeds.setText(myseeds.get(position));
        holder.farm.setText(myfarm.get(position));
        holder.villName.setText("My Village");
        String mystatsus =mystatus.get(position);

       if(mystatsus.equals("1")){
           holder.btnStatus.setText("Online");
       } else{
           holder.btnStatus.setText("Offline");
       }

    }

    @Override
    public int getItemCount() {
        return myforcastid.size();
    }

    public static class HarvestForcastingViewHolder extends RecyclerView.ViewHolder {
        TextView area,cropDate,date,farm,plant,seeds,time,farmerName,villName;
        Button btnStatus;
        public HarvestForcastingViewHolder(View view) {
            super(view);
            area = view.findViewById(R.id.area);
            cropDate = view.findViewById(R.id.cropDate);
            //date = view.findViewById(R.id.date);
            farm = view.findViewById(R.id.farmerName);
            plant = view.findViewById(R.id.plant);
            seeds = view.findViewById(R.id.seeds);
            //time = view.findViewById(R.id.time);
            farmerName = view.findViewById(R.id.farmerName);
            villName = view.findViewById(R.id.villName);
            btnStatus = view.findViewById(R.id.buttonstatus);

        }
    }
}

package com.swift.akc.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.swift.akc.R;
import com.swift.akc.adapters.HarvestForecastingListAdapter;
import com.swift.akc.adapters.HarvestForecastingListAdapterSql;
import com.swift.akc.adapters.HarvestVisitListAdapter;
import com.swift.akc.database.CommonUtil;
import com.swift.akc.database.DatabaseHelper;
import com.swift.akc.database.DatabaseUtil;
import com.swift.akc.extras.Constants;
import com.swift.akc.network.ApiEndpoint;
import com.swift.akc.network.data.HarvestForcastingListVO;
import com.swift.akc.network.data.HarvestForcastingVO;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HarvestForecastingListFragment extends BaseFragment {

    private HarvestForecastingListAdapter mAdapter;

    private RecyclerView mRecyclerView;

    Context context;
    Boolean mynetwork;

    public HarvestForecastingListFragment() {

    }

    public static HarvestForecastingListFragment newInstance(String title) {
        HarvestForecastingListFragment comingSoonFragment = new HarvestForecastingListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.INTENT_PARAM_TITLE, title);
        comingSoonFragment.setArguments(bundle);
        return comingSoonFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mParentView = inflater.inflate(R.layout.fr_harvest_forcasting, container, false);
        context = getActivity();
        mRecyclerView = mParentView.findViewById(R.id.recycler_view);
        CommonUtil.databaseUtil = new DatabaseUtil(context);
        CommonUtil.databaseUtil.open();
        CommonUtil.databaseHelper = new DatabaseHelper(context);
        CommonUtil.pref = getActivity().getSharedPreferences(CommonUtil.MyPreferances, Context.MODE_PRIVATE);
        mynetwork = CommonUtil.pref.getBoolean("NetworkCon",false);

        return mParentView;
    }

    @NonNull
    @Nullable
    public void onViewCreated(@NonNull View view, @io.reactivex.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Log.e("Testing","ListView");
        Cursor cur =  CommonUtil.databaseUtil.getHarvestForcasting();
        Log.e("Testing","Sucess" + cur.getCount());



        ArrayList<Integer> myforcastid = new ArrayList<>();
        ArrayList<String> myfarm = new ArrayList<>();
        ArrayList<String> myforcastarea = new ArrayList<>();
        ArrayList<String> myVillagearea = new ArrayList<>();

        ArrayList<String> myplant = new ArrayList<>();
        ArrayList<String> mydate = new ArrayList<>();
        ArrayList<String> mytime = new ArrayList<>();
        ArrayList<String> mycropDate = new ArrayList<>();
        ArrayList<String> myseeds = new ArrayList<>();
        ArrayList<String> mystatus = new ArrayList<>();

        if(!mynetwork) {


            if (cur.moveToFirst()) {
                try {
                    do {
                        int forcastId = cur.getInt(cur.getColumnIndexOrThrow(DatabaseHelper.ID_CL));
                        String farm = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.FORECAST_FARM_ID));
                        String forcastArea = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.FORECAST_CULTIVATION));
                        String plant = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.FORECAST_PLANT_SEED));
                        String date = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.FORECAST_ENTRY_DATE));
                        String time = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.FORECAST_ENTRY_TIME));
                        String cropDate = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.FORECAST_SOWING_DATE));
                        String seeds = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.FORECAST_SOWING_KG));
                        String status = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.STATUS));

                        Cursor cussr = CommonUtil.databaseUtil.getFarmbyId(farm);
                        if (cussr.moveToFirst()) {

                            String farmname = cussr.getString(cussr.getColumnIndexOrThrow(DatabaseHelper.SQL_FARM_NAME));
                            String villagename = cussr.getString(cussr.getColumnIndexOrThrow(DatabaseHelper.SQL_VILLAGE_NAME));

                            myfarm.add(farmname);
                            myVillagearea.add(villagename);

                        }
                        Cursor curp = CommonUtil.databaseUtil.getPlantseebydid(plant);
                        if (curp.moveToFirst()) {

                            String strplantseed = curp.getString(curp.getColumnIndexOrThrow(DatabaseHelper.SQL_PLANT_NAME));
                            myplant.add(strplantseed);

                        }


                        myforcastarea.add(forcastArea);
                        myforcastid.add(forcastId);
                        mydate.add(date);
                        mytime.add(time);
                        mycropDate.add(cropDate);
                        myseeds.add(seeds);
                        mystatus.add(status);

                        Log.e("Testing", "" + forcastId);
                        Log.e("Testing", "" + myforcastid.size());


                    } while (cur.moveToNext());
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }

            HarvestForecastingListAdapterSql adapterSql = new HarvestForecastingListAdapterSql(getActivity(), myforcastid,
                    myfarm, myforcastarea, myplant, mydate, mytime, mycropDate, myseeds, mystatus, myVillagearea);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            mRecyclerView.setAdapter(adapterSql);
        } else{
            mAdapter = new HarvestForecastingListAdapter(getActivity());

            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            mRecyclerView.setAdapter(mAdapter);
            getHarvestForcastingList();
        }
//        getHarvestForcastingList();
    }

//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        inflater.inflate(R.menu.filter_menu, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId() == R.id.icon_filter) {
//            switchFragment(LandingPageActivity.FRAGMENT_HARVEST_FILTER, "Filter", true);
//        }
//        return super.onOptionsItemSelected(item);
//    }

    public void getHarvestForcastingList() {
        showLoading();
        Rx2AndroidNetworking
                .get(ApiEndpoint.HARVEST_FORCASTING_LIST_API)
                .build()
                .getObjectObservable(HarvestForcastingListVO.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HarvestForcastingListVO>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HarvestForcastingListVO listVO) {
                        mAdapter.refresh(listVO.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        hideLoading();
                    }
                });

    }
}

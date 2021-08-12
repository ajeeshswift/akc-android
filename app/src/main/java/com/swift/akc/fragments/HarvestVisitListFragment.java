package com.swift.akc.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.swift.akc.R;
import com.swift.akc.activity.LandingPageActivity;
import com.swift.akc.adapters.HarvestVisitListAdapter;
import com.swift.akc.adapters.HarvestVisitListAdapterSql;
import com.swift.akc.database.CommonUtil;
import com.swift.akc.database.DatabaseHelper;
import com.swift.akc.database.DatabaseUtil;
import com.swift.akc.extras.Constants;
import com.swift.akc.network.ApiEndpoint;
import com.swift.akc.network.data.HarvestVisitListVO;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HarvestVisitListFragment extends BaseFragment {

    private HarvestVisitListAdapter mAdapter;

    private RecyclerView mRecyclerView;
    Context context;

    public HarvestVisitListFragment() {

    }

    public static HarvestVisitListFragment newInstance(String title) {
        HarvestVisitListFragment comingSoonFragment = new HarvestVisitListFragment();
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
        mParentView = inflater.inflate(R.layout.fr_harvest_visit, container, false);
        mRecyclerView = mParentView.findViewById(R.id.recycler_view);
        return mParentView;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.filter_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.icon_filter) {
            switchFragment(LandingPageActivity.FRAGMENT_HARVEST_FILTER, "Filter", true);
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Nullable
    public void onViewCreated(@NonNull View view, @io.reactivex.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();


        CommonUtil.databaseUtil = new DatabaseUtil(context);
        CommonUtil.databaseUtil.open();
        CommonUtil.databaseHelper = new DatabaseHelper(context);
        CommonUtil.pref = getActivity().getSharedPreferences(CommonUtil.MyPreferances, Context.MODE_PRIVATE);


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
        ArrayList<String> myStatus          = new ArrayList<>();
        Log.e("Testing","ListView");
        Cursor cur =  CommonUtil.databaseUtil.getHarvestVisitList();
        Log.e("Testing","Sucess" + cur.getCount());

        if(cur.moveToFirst()) {

            try {
                do {
                    int florastid = cur.getInt(cur.getColumnIndexOrThrow(DatabaseHelper.FLORA_ST_ID));
                    String plantseed = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.PLANTSEED));
                    String sowing_date = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.SOWING_DATE));
                    String slapping_qty = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.SAPPLING_QUANTITY));
                    String harvest_date = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.HARVEST_DATE));
                    String harvest_qty = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.HARVEST_QUANTITY));
                    String home_use = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.OWN_HOME_USE));
                    String sold_qty = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.SOLD_QUANTITY));
                    String sold_rate = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.SOLD_RATE));
                    String total_income = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.TOTAL_INCOME));
                    String status = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.STATUS));

                    myflorastid.add(florastid);
                    myfloraId.add(plantseed);
                    mystrsowingDate.add(sowing_date);
                    mystrsapQuantity.add(slapping_qty);
                    mystrsoldQuantity.add(sold_qty);
                    mystrharvestDate.add(harvest_date);
                    mystrharvestQuantity.add(harvest_qty);
                    mystrownUseQuantity.add(home_use);
                    mystrsoldRate.add(sold_rate);
                    mystrtotalIncome.add(total_income);
                    myStatus.add(status);


                    Log.e("Testing", "" + florastid);
                    Log.e("Testing", "" + myfloraId.size());


                } while (cur.moveToNext());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }


        //mAdapter = new HarvestVisitListAdapter(getActivity());
        HarvestVisitListAdapterSql harvestVisitListAdapterSql = new HarvestVisitListAdapterSql(getActivity(),mystrsowingDate,
                mystrsapQuantity,mystrsoldQuantity,mystrharvestDate,mystrharvestQuantity,
                mystrownUseQuantity,mystrsoldRate,mystrtotalIncome,myStatus);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(harvestVisitListAdapterSql);
      //  getHarvestList();
    }

    public void getHarvestList() {
        showLoading();
        Rx2AndroidNetworking
                .get(ApiEndpoint.HARVEST_VISIT_LIST_API)
                .build()
                .getObjectObservable(HarvestVisitListVO.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HarvestVisitListVO>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HarvestVisitListVO listVO) {
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

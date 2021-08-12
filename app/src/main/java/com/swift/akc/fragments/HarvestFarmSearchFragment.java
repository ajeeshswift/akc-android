package com.swift.akc.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.swift.akc.R;
import com.swift.akc.activity.LandingPageActivity;
import com.swift.akc.database.CommonUtil;
import com.swift.akc.database.DatabaseHelper;
import com.swift.akc.database.DatabaseUtil;
import com.swift.akc.extras.Constants;
import com.swift.akc.extras.EntryType;
import com.swift.akc.extras.Storage;
import com.swift.akc.network.ApiEndpoint;
import com.swift.akc.network.data.HarvestFarmVO;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HarvestFarmSearchFragment extends BaseFragment implements View.OnClickListener {

    EditText farmno;
    Button submit;
    Context context;

    public HarvestFarmSearchFragment() {

    }

    public static HarvestFarmSearchFragment newInstance(String title) {
        HarvestFarmSearchFragment comingSoonFragment = new HarvestFarmSearchFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.INTENT_PARAM_TITLE, title);
        comingSoonFragment.setArguments(bundle);
        return comingSoonFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mParentView = inflater.inflate(R.layout.fr_harvest_farm_search, container, false);
        context = getActivity();

        CommonUtil.databaseUtil = new DatabaseUtil(context);
        CommonUtil.databaseUtil.open();
        CommonUtil.databaseHelper = new DatabaseHelper(context);
        CommonUtil.pref = getActivity().getSharedPreferences(CommonUtil.MyPreferances, Context.MODE_PRIVATE);

        farmno = mParentView.findViewById(R.id.farmno);
        submit = mParentView.findViewById(R.id.submit);
        farmno.requestFocus();
        submit.setOnClickListener(this);
        return mParentView;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.submit) {
            validate();
        }
    }

    private void validate() {
        String strfarmno =farmno.getText().toString().trim();

        if (strfarmno.matches("")) {
            farmno.setError("Enter Farmer Number");
            farmno.requestFocus();
            return;
        }

        Cursor cur =  CommonUtil.databaseUtil.getFarmId(strfarmno);

        if(cur.getCount() >0 && cur.moveToFirst()){

            String farmid = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.SQL_FARM_ID));
            String farmname = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.SQL_FARM_NAME));

            Log.e("Testing",""+ farmid + "farm name" + farmname);

            SharedPreferences.Editor sharedEditor = CommonUtil.pref.edit();
            sharedEditor.putString("FARMID",farmid);
            sharedEditor.apply();
            openNextFragment();

          //  switchFragment(LandingPageActivity.FRAGMENT_HARVEST_VISIT_ENTRY, "Harvest Entry", true);

        }else{
            Log.e("Testing",""+cur.getCount());
        }





        // harvestVisitEntryPageAPICall();
    }

    private void harvestVisitEntryPageAPICall() {
        showLoading();
        Rx2AndroidNetworking.get(ApiEndpoint.FARMER_DETAILS_API)
                .addPathParameter("farmNo", farmno.getText().toString())
                .build()
                .getObjectObservable(HarvestFarmVO.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HarvestFarmVO>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HarvestFarmVO harvestFarmVO) {
                        Storage.selectedHarvestFarm = harvestFarmVO;
                        //Toast.makeText(getActivity(), "", Toast.LENGTH_LONG).show();
                        openNextFragment();
                    }

                    @Override
                    public void onError(Throwable e) {
                        showApiError(e);
                        hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        hideLoading();
                    }
                });
    }

    public void openNextFragment() {
        if (Storage.selectedMenu.equals(EntryType.HARVEST_FORECASTING_ENTRY)) {
            switchFragment(LandingPageActivity.FRAGMENT_HARVEST_FORECASTING_ENTRY, "Harvest Entry", true);
            return;
        }
        switchFragment(LandingPageActivity.FRAGMENT_HARVEST_VISIT_ENTRY, "Harvest Entry", true);
    }
}

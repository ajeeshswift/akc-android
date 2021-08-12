package com.swift.akc.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.swift.akc.R;
import com.swift.akc.activity.LandingPageActivity;
import com.swift.akc.adapters.PlantSeedListAdapter;
import com.swift.akc.database.CommonUtil;
import com.swift.akc.database.DatabaseHelper;
import com.swift.akc.database.DatabaseUtil;
import com.swift.akc.extras.Constants;
import com.swift.akc.extras.Storage;
import com.swift.akc.extras.UIValidation;
import com.swift.akc.helper.ui.DatePickerView;
import com.swift.akc.network.ApiEndpoint;
import com.swift.akc.network.data.HarvestFarmVO;
import com.swift.akc.network.data.HarvestVO;
import com.swift.akc.network.data.PlantSeedListVO;
import com.swift.akc.network.data.PlantSeedVO;
import com.swift.akc.utils.DateUtils;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HarvestVisitEntryFragment extends BaseFragment implements TextWatcher,
        View.OnClickListener, AdapterView.OnItemClickListener {

    EditText sowingDate, sapQuantity, harvestQuantity,
            ownUse, soldQuantity, soldRate, totalIncome, harvestDate;
    Button submit;
    TextView tvw;
    DatePickerDialog picker;
    AutoCompleteTextView plantOrSeed;
    String Dest;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String currentDate = sdf.format(new Date());

    private PlantSeedListAdapter mAdapter;

    private PlantSeedVO plantSeedVO;
    Context context;

    Boolean mynetwork;
    String strMyfarmid;
    List<String> myplantorSeed;
    ArrayAdapter<String> mylistPlantSeed;
    public HarvestVisitEntryFragment() {

    }

    public static HarvestVisitEntryFragment newInstance(String title) {
        HarvestVisitEntryFragment comingSoonFragment = new HarvestVisitEntryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.INTENT_PARAM_TITLE, title);
        comingSoonFragment.setArguments(bundle);
        return comingSoonFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mParentView = inflater.inflate(R.layout.fr_harvest_visit_entry, container, false);
        context = getActivity();
        return mParentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] fruits = {"Apple", "Apple1", "Apple2", "Apple3", "Apple4", "Apple5", "Banana", "Cherry", "Date", "Grape", "Kiwi", "Mango", "Pear"};

        CommonUtil.databaseUtil = new DatabaseUtil(context);
        CommonUtil.databaseUtil.open();
        CommonUtil.databaseHelper = new DatabaseHelper(context);
        CommonUtil.pref = getActivity().getSharedPreferences(CommonUtil.MyPreferances, Context.MODE_PRIVATE);

        tvw = (TextView) mParentView.findViewById(R.id.textView1);

        plantOrSeed = mParentView.findViewById(R.id.autoCompletePlantsseed);
;
//        mAdapter = new PlantSeedListAdapter(getActivity(), R.layout.item_autocomplete, new ArrayList<>());
//        plantOrSeed.setAdapter(mAdapter);


        harvestDate = (EditText) mParentView.findViewById(R.id.harvestdate);
        sowingDate = (EditText) mParentView.findViewById(R.id.sowingdate);
        sapQuantity = (EditText) mParentView.findViewById(R.id.sapplingquantity);
        harvestQuantity = (EditText) mParentView.findViewById(R.id.harvestquantity);
        ownUse = (EditText) mParentView.findViewById(R.id.ownhomeuse);
        soldQuantity = (EditText) mParentView.findViewById(R.id.soldquantity);
        soldRate = (EditText) mParentView.findViewById(R.id.soldrate);
        totalIncome = (EditText) mParentView.findViewById(R.id.totalincome);
        submit = (Button) mParentView.findViewById(R.id.submit);
        submit.setOnClickListener(this);

        harvestDate.setInputType(InputType.TYPE_NULL);
        harvestDate.setText(currentDate);

        mynetwork = CommonUtil.pref.getBoolean("NetworkCon",false);
        strMyfarmid  = CommonUtil.pref.getString("FARMID","0");

       myplantorSeed = CommonUtil.databaseUtil.getPlantseed();

        mylistPlantSeed = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,myplantorSeed);
        plantOrSeed.setAdapter(mylistPlantSeed);
     //   plantOrSeed.addTextChangedListener(this);
        plantOrSeed.setOnItemClickListener(this);
        plantOrSeed.setThreshold(1);




        harvestDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                DatePickerView datePickerView = new DatePickerView();
                datePickerView.setDatePickerView(getActivity(), harvestDate);
            }
        });


        sowingDate.setInputType(InputType.TYPE_NULL);
        sowingDate.setText(currentDate);
        sowingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                DatePickerView datePickerView = new DatePickerView();
                datePickerView.setDatePickerView(getActivity(), sowingDate);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.submit) {
            validate();
        }
    }

    private void validate() {
        if (plantOrSeed.getText().toString().matches("")) {
            plantOrSeed.setError("Enter Plant/Seed Name");
            plantOrSeed.requestFocus();
            return;
        }

        if (sowingDate.getText().toString().matches("")) {
            sowingDate.setError("Choose Sowing Date");
            sowingDate.requestFocus();
            return;
        }

        if (sapQuantity.getText().toString().matches("")) {
            sapQuantity.setError("Enter Sappling QTY");
            sapQuantity.requestFocus();
            return;
        }

        if (harvestDate.getText().toString().matches("")) {
            harvestDate.setError("Choose Harvest Date");
            harvestDate.requestFocus();
            return;
        }

        if (harvestQuantity.getText().toString().matches("")) {
            harvestQuantity.setError("Choose Harvest QTY");
            harvestQuantity.requestFocus();
            return;
        }

        if (ownUse.getText().toString().matches("")) {
            ownUse.setError("Enter Own Use");
            ownUse.requestFocus();
            return;
        }

        if (soldQuantity.getText().toString().matches("")) {
            soldQuantity.setError("Enter Sold QTY");
            soldQuantity.requestFocus();
            return;
        }

        if (soldRate.getText().toString().matches("")) {
            soldRate.setError("Enter Sold Rate");
            soldRate.requestFocus();
            return;
        }

        if (totalIncome.getText().toString().matches("")) {
            totalIncome.setError("Enter Total Income");
            totalIncome.requestFocus();
            return;
        }
        harvestVisitEntryAPICall();
    }

    private void harvestVisitEntryAPICall() {
        int floraId = Integer.parseInt(strMyfarmid);
        String strsowingDate =  DateUtils.convertDateFormat(sowingDate.getText().toString());
        String strsapQuantity = sapQuantity.getText().toString();
        String strharvestDate =DateUtils.convertDateFormat(harvestDate.getText().toString());
        String strharvestQuantity = harvestQuantity.getText().toString();
        String strownUseQuantity = ownUse.getText().toString();
        String strsoldQuantity = soldQuantity.getText().toString();
        String strsoldRate =soldRate.getText().toString();
        String strtotalIncome = totalIncome.getText().toString();
        int strfarmId = Storage.selectedHarvestFarm.getFarmId();
        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());



        if (mynetwork) {

            CommonUtil.databaseUtil.addHarvest(floraId,strsowingDate,strsapQuantity,
                    strharvestDate,strharvestQuantity,strownUseQuantity,strsoldQuantity,
                    strsoldRate,strtotalIncome,strfarmId,currentDateTimeString);


        } else {
            JSONObject params = new JSONObject();
            try {
                params.put("floraId", plantSeedVO.getId());
                params.put("sowingDate", DateUtils.convertDateFormat(sowingDate.getText().toString()));
                params.put("sapQuantity", sapQuantity.getText().toString());
                params.put("harvestDate", DateUtils.convertDateFormat(harvestDate.getText().toString()));
                params.put("harvestQuantity", harvestQuantity.getText().toString());
                params.put("ownUseQuantity", ownUse.getText().toString());
                params.put("soldQuantity", soldQuantity.getText().toString());
                params.put("soldRate", soldRate.getText().toString());
                params.put("totalIncome", totalIncome.getText().toString());
                params.put("farmId", Storage.selectedHarvestFarm.getFarmId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            showLoading();
            Rx2AndroidNetworking.post(ApiEndpoint.HARVEST_API)
                    .addJSONObjectBody(params)
                    .build()
                    .getObjectObservable(HarvestVO.class)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<HarvestVO>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(HarvestVO object) {
                            CommonUtil.databaseUtil.updateHarevest(floraId);
                            Toast.makeText(getActivity(), "Successfully Added", Toast.LENGTH_LONG).show();
                            harvestDate.setText(currentDate);
                            sowingDate.setText(currentDate);
                            sapQuantity.setText("");
                            harvestQuantity.setText("");
                            ownUse.setText("");
                            soldQuantity.setText("");
                            soldRate.setText("");
                            totalIncome.setText("");
                            switchFragment(LandingPageActivity.FRAGMENT_HARVEST_FARM_SEARCH, "Harvest Entry", true);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(getActivity(), " ", Toast.LENGTH_LONG).show();
                            hideLoading();
                        }

                        @Override
                        public void onComplete() {
                            hideLoading();
                        }
                    });
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
//        if (plantOrSeed.isPerformingCompletion()) {
//            // An item has been selected from the list. Ignore.
//            return;
//        }
        //autoCompletePlantOrSeed(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//        plantSeedVO = (PlantSeedVO) parent.getItemAtPosition(position);
        plantOrSeed.setText(mylistPlantSeed.getItem(position));

    }

//    private void autoCompletePlantOrSeed(String query) {
//        Rx2AndroidNetworking.get(ApiEndpoint.FLORA_AUTOCOMPLETE_API)
//                .addQueryParameter("query", query)
//                .build()
//                .getObjectObservable(PlantSeedListVO.class)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<PlantSeedListVO>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(PlantSeedListVO plantSeedListVO) {
//                        mAdapter.refresh(plantSeedListVO.getData());
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        showApiError(e);
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        hideLoading();
//                    }
//                });
//    }


}

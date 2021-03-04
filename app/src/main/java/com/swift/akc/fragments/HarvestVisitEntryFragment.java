package com.swift.akc.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.swift.akc.extras.Constants;
import com.swift.akc.extras.Storage;
import com.swift.akc.extras.UIValidation;
import com.swift.akc.helper.ui.DatePickerView;
import com.swift.akc.network.ApiEndpoint;
import com.swift.akc.network.data.HarvestVO;
import com.swift.akc.utils.DateUtils;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HarvestVisitEntryFragment extends BaseFragment implements View.OnClickListener {

    EditText sowingDate, sapQuantity, harvestQuantity,
            ownUse, soldQuantity, soldRate, totalIncome, harvestDate;
    Button submit;
    TextView tvw;
    DatePickerDialog picker;
    AutoCompleteTextView plantOrSeed;
    String Dest;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String currentDate = sdf.format(new Date());

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
        return mParentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] fruits = {"Apple", "Apple1", "Apple2", "Apple3", "Apple4", "Apple5", "Banana", "Cherry", "Date", "Grape", "Kiwi", "Mango", "Pear"};
        tvw = (TextView) mParentView.findViewById(R.id.textView1);
        plantOrSeed = (AutoCompleteTextView) mParentView.findViewById(R.id.autoCompletePlantsseed);
        harvestDate = (EditText) mParentView.findViewById(R.id.harvestdate);
        sowingDate = (EditText) mParentView.findViewById(R.id.sowingdate);
        sapQuantity = (EditText) mParentView.findViewById(R.id.sapplingquantity);
        harvestQuantity = (EditText) mParentView.findViewById(R.id.harvestquantity);
        ownUse = (EditText) mParentView.findViewById(R.id.ownhomeuse);
        soldQuantity = (EditText) mParentView.findViewById(R.id.soldquantity);
        soldRate = (EditText) mParentView.findViewById(R.id.soldrate);
        totalIncome = (EditText) mParentView.findViewById(R.id.totalincome);
        submit = (Button) mParentView.findViewById(R.id.submit);
        plantOrSeed.requestFocus();
        submit.setOnClickListener(this);

        //Creating the instance of ArrayAdapter containing list of fruit names
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.select_dialog_item, fruits);
        //Getting the instance of AutoCompleteTextView

        plantOrSeed.setThreshold(1);//will start working from first character
        plantOrSeed.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        plantOrSeed.setTextColor(Color.RED);

        harvestDate.setInputType(InputType.TYPE_NULL);
        harvestDate.setText(currentDate);
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
    public void onClick(View view) {
        if (view.getId() == R.id.submit) {
            harvestVisitEntryAPICall();
        }
    }

    private void harvestVisitEntryAPICall() {
        UIValidation uiValidation = new UIValidation();

        if (sowingDate.getText().toString().matches("")) {
            uiValidation.validateFields(getActivity(), sowingDate, sowingDate.getText().toString(), "Choose Sowing Date");
        } else if (sapQuantity.getText().toString().matches("")) {
            uiValidation.validateFields(getActivity(), sapQuantity, sapQuantity.getText().toString(), "Enter Sappling Quantity");
        } else if (harvestDate.getText().toString().matches("")) {
            uiValidation.validateFields(getActivity(), harvestDate, harvestDate.getText().toString(), "Choose Harvest Date");
        } else if (harvestQuantity.getText().toString().matches("")) {
            uiValidation.validateFields(getActivity(), harvestQuantity, harvestQuantity.getText().toString(), "Enter Harvest Quantity");
        } else if (ownUse.getText().toString().matches("")) {
            uiValidation.validateFields(getActivity(), ownUse, ownUse.getText().toString(), "Enter Own Use");
        } else if (soldQuantity.getText().toString().matches("")) {
            uiValidation.validateFields(getActivity(), soldQuantity, soldQuantity.getText().toString(), "Enter Sold Quantity");
        } else if (soldRate.getText().toString().matches("")) {
            uiValidation.validateFields(getActivity(), soldRate, soldRate.getText().toString(), "Enter Sold Rate");
        } else if (totalIncome.getText().toString().matches("")) {
            uiValidation.validateFields(getActivity(), totalIncome, totalIncome.getText().toString(), "Enter Total Income");
        } else {
            JSONObject params = new JSONObject();
            try {
                //params.put("plantOrSeed",plantOrSeed.getText().toString());
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
                            Toast.makeText(getActivity(), "Successfully Added", Toast.LENGTH_LONG).show();
                            harvestDate.setText(currentDate);
                            sowingDate.setText(currentDate);
                            sapQuantity.setText("");
                            harvestQuantity.setText("");
                            ownUse.setText("");
                            soldQuantity.setText("");
                            soldRate.setText("");
                            totalIncome.setText("");
                            switchFragment(LandingPageActivity.FRAGMENT_HARVEST_FARM_SEARCH,"Harvest Entry", true);
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
}

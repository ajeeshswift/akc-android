package com.swift.akc.fragments;

import android.os.Bundle;
import android.text.InputType;
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
import com.swift.akc.extras.Constants;
import com.swift.akc.extras.Storage;
import com.swift.akc.extras.UIValidation;
import com.swift.akc.helper.ui.DatePickerView;
import com.swift.akc.network.ApiEndpoint;
import com.swift.akc.network.data.HarvestVO;
import com.swift.akc.network.data.HarvestVisitListVO;
import com.swift.akc.utils.DateUtils;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HarvestFilterFragment extends BaseFragment  implements View.OnClickListener {

    public HarvestFilterFragment() {

    }

    EditText DateFrom, DateTo;
    Button submit;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String currentDate = sdf.format(new Date());

    public static HarvestFilterFragment newInstance(String title) {
        HarvestFilterFragment comingSoonFragment = new HarvestFilterFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.INTENT_PARAM_TITLE, title);
        comingSoonFragment.setArguments(bundle);
        return comingSoonFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mParentView = inflater.inflate(R.layout.fr_harvest_filter, container, false);
        return mParentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DateFrom = (EditText) mParentView.findViewById(R.id.harvestDateFrom);
        DateTo = (EditText) mParentView.findViewById(R.id.harvestDateTo);

        submit = (Button) mParentView.findViewById(R.id.submit);
        DateFrom.requestFocus();
        submit.setOnClickListener((View.OnClickListener) this);


        DateFrom.setInputType(InputType.TYPE_NULL);
        DateFrom.setText(currentDate);
        DateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                DatePickerView datePickerView = new DatePickerView();
                datePickerView.setDatePickerView(getActivity(), DateFrom);
            }
        });


        DateTo.setInputType(InputType.TYPE_NULL);
        DateTo.setText(currentDate);
        DateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                DatePickerView datePickerView = new DatePickerView();
                datePickerView.setDatePickerView(getActivity(), DateTo);
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

        if (DateFrom.getText().toString().matches("")) {
            uiValidation.validateFields(getActivity(), DateFrom, DateFrom.getText().toString(), "Choose Sowing Date");
        } else if (DateTo.getText().toString().matches("")) {
            uiValidation.validateFields(getActivity(), DateTo, DateTo.getText().toString(), "Choose Harvest Date");
        }  else {
            JSONObject params = new JSONObject();
            try {
                //params.put("plantOrSeed",plantOrSeed.getText().toString());
                params.put("DateFrom", DateUtils.convertDateFormat(DateFrom.getText().toString()));

                params.put("DateTo", DateUtils.convertDateFormat(DateTo.getText().toString()));

            } catch (Exception e) {
                e.printStackTrace();
            }
            showLoading();
            Rx2AndroidNetworking.post(ApiEndpoint.HARVEST_VISIT_BET_DATE_API)
                    .addJSONObjectBody(params)
                    .build()
                    .getObjectObservable(HarvestVisitListVO.class)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<HarvestVisitListVO>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(HarvestVisitListVO object) {
                            Toast.makeText(getActivity(), "Successfully Added", Toast.LENGTH_LONG).show();
                            DateFrom.setText(currentDate);
                            DateTo.setText(currentDate);
                            switchFragment(LandingPageActivity.FRAGMENT_HARVEST_FILTER,"Harvest Entry", true);
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

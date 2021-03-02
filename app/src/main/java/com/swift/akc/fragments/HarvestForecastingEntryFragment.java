package com.swift.akc.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.swift.akc.R;
import com.swift.akc.extras.Constants;
import com.swift.akc.extras.Storage;
import com.swift.akc.network.ApiEndpoint;
import com.swift.akc.network.data.HarvestForcastingVO;
import com.swift.akc.utils.DateUtils;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HarvestForecastingEntryFragment extends BaseFragment implements View.OnClickListener {

    public HarvestForecastingEntryFragment() {

    }

    public static HarvestForecastingEntryFragment newInstance(String title) {
        HarvestForecastingEntryFragment comingSoonFragment = new HarvestForecastingEntryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.INTENT_PARAM_TITLE, title);
        comingSoonFragment.setArguments(bundle);
        return comingSoonFragment;
    }

    EditText villagename, crop, seedsown, cultivation, sowingdate;
    DatePickerDialog picker;
    Button submit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mParentView = inflater.inflate(R.layout.fr_harvest_forecasting_entry, container, false);
        return mParentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        villagename = (EditText) mParentView.findViewById(R.id.villagename);
        crop = (EditText) mParentView.findViewById(R.id.crop);
        seedsown = (EditText) mParentView.findViewById(R.id.seedsown);
        cultivation = (EditText) mParentView.findViewById(R.id.cultivation);
        sowingdate = (EditText) mParentView.findViewById(R.id.sowingdate);
        submit = (Button) mParentView.findViewById(R.id.submit);
        submit.setOnClickListener(this);

        sowingdate.setInputType(InputType.TYPE_NULL);
        sowingdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                picker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                sowingdate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.submit) {
            harvestingforcastAPICall();
        }
    }

    private void harvestingforcastAPICall() {
        JSONObject params = new JSONObject();
        try {
            params.put("plantId", crop.getText().toString());
            params.put("seeds", seedsown.getText().toString());
            params.put("area", cultivation.getText().toString());
            params.put("cropShowingDate", DateUtils.convertDateFormat(sowingdate.getText().toString()));
            params.put("farmId", 1);
            params.put("uid", 1);
            params.put("date", new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
            params.put("time", new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()));
            params.put("farmId", Storage.selectedHarvestFarm.getFarmId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Rx2AndroidNetworking.post(ApiEndpoint.HARVEST_FORECAST_API)
                .addJSONObjectBody(params)
                .build()
                .getObjectObservable(HarvestForcastingVO.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HarvestForcastingVO>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HarvestForcastingVO object) {
                        Toast.makeText(getActivity(), "Successfully Added", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideLoading();
                        showApiError(e);

                    }

                    @Override
                    public void onComplete() {
                        hideLoading();
                    }
                });
    }
}

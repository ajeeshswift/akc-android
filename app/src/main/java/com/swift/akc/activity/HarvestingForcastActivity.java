package com.swift.akc.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.swift.akc.utils.DateUtils;
import com.swift.akc.R;
import com.swift.akc.network.ApiEndpoint;
import com.swift.akc.network.data.HarvestForcastingVO;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HarvestingForcastActivity extends LandingPageActivity implements View.OnClickListener {
    EditText villagename,crop,seedsown,cultivation,sowingdate;
    DatePickerDialog picker;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harvestingforcast);

        villagename = (EditText)findViewById(R.id.villagename);
        crop = (EditText)findViewById(R.id.crop);
        seedsown = (EditText)findViewById(R.id.seedsown);
        cultivation = (EditText)findViewById(R.id.cultivation);
        sowingdate = (EditText)findViewById(R.id.sowingdate);
        submit = (Button)findViewById(R.id.submit);
        submit.setOnClickListener(this);

        sowingdate.setInputType(InputType.TYPE_NULL);
        sowingdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                picker = new DatePickerDialog(HarvestingForcastActivity.this,
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
    public void onClick(View view){
        if(view.getId() == R.id.submit) {
            try {
                harvestingforcastAPICall();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    private void harvestingforcastAPICall() throws ParseException {
        JSONObject params = new JSONObject();
        try {
            params.put("plantId",crop.getText().toString());
            params.put("seeds",seedsown.getText().toString());
            params.put("area",cultivation.getText().toString());
            params.put("cropShowingDate", DateUtils.convertDateFormat(sowingdate.getText().toString()));
            params.put("farmId",1);
            params.put("uid",1);
            params.put("date",new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
            params.put("time",new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()));
        }
        catch (Exception e){
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
                    Toast.makeText(HarvestingForcastActivity.this, "Successfully Added", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(Throwable e) {
                    Toast.makeText(HarvestingForcastActivity.this, "Error", Toast.LENGTH_LONG).show();
                    hideLoading();
                }

                @Override
                public void onComplete() {
                    hideLoading();
                }
            });
        }
    }

package com.swift.akc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.swift.akc.network.ApiEndpoint;
import com.swift.akc.network.data.HarvestVO;
import org.json.JSONObject;
import java.util.Calendar;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class harvestvisit extends AppCompatActivity implements View.OnClickListener {
    EditText plantOrSeed,sowingDate,sapQuantity,harvestQuantity,
            ownUse,soldQuantity,soldRate,totalIncome,harvestDate;
    Button submit;
    TextView tvw;
    DatePickerDialog picker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harvestvisit);

            tvw             = (TextView)findViewById(R.id.textView1);
            plantOrSeed     = (EditText)findViewById(R.id.plantsseed);
            harvestDate     = (EditText) findViewById(R.id.harvestdate);
            sowingDate      = (EditText) findViewById(R.id.sowingdate);
            sapQuantity     = (EditText)findViewById(R.id.sapplingquantity);
            harvestQuantity = (EditText)findViewById(R.id.harvestquantity);
            ownUse          = (EditText)findViewById(R.id.ownhomeuse);
            soldQuantity    = (EditText)findViewById(R.id.soldquantity);
            soldRate        = (EditText)findViewById(R.id.soldrate);
            totalIncome     = (EditText)findViewById(R.id.totalincome);
            submit          = (Button) findViewById(R.id.submit);
            submit.setOnClickListener(this);


        harvestDate.setInputType(InputType.TYPE_NULL);
        harvestDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(harvestvisit.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                harvestDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }

    });

        sowingDate.setInputType(InputType.TYPE_NULL);
        sowingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(harvestvisit.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                sowingDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });


    }
    @Override
    public void onClick(View view){
        if(view.getId() == R.id.submit){
            harvestVisitEntryAPICall();
        }
    }
    private void harvestVisitEntryAPICall(){
        JSONObject params = new JSONObject();
        try{
            params.put("plantOrSeed",plantOrSeed.getText().toString());
            params.put("sowingDate",sowingDate.getText().toString());
            params.put("sapQuantity",sapQuantity.getText().toString());
            params.put("harvestDate",harvestDate.getText().toString());
            params.put("harvestQuantity",harvestQuantity.getText().toString());
            params.put("ownUseQuantity",ownUse.getText().toString());
            params.put("soldQuantity",soldQuantity.getText().toString());
            params.put("soldRate",soldRate.getText().toString());
            params.put("totalIncome",totalIncome.getText().toString());
        }
        catch (Exception e){
            e.printStackTrace();
        }

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
                        Toast.makeText(harvestvisit.this, "Successfully Added", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(harvestvisit.this, " ", Toast.LENGTH_LONG).show();
                        //hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        //hideLoading();
                    }
                });
         }
    }


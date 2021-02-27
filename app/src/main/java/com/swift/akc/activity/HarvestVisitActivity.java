package com.swift.akc.activity;

import androidx.annotation.Nullable;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.swift.akc.utils.DateUtils;
import com.swift.akc.helper.ui.DatePickerView;
import com.swift.akc.R;
import com.swift.akc.network.ApiEndpoint;
import com.swift.akc.network.data.HarvestVO;
import org.json.JSONObject;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import android.graphics.Color;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class HarvestVisitActivity extends LandingPageActivity implements View.OnClickListener {


    EditText sowingDate,sapQuantity,harvestQuantity,
            ownUse,soldQuantity,soldRate,totalIncome,harvestDate;
    Button submit;
    TextView tvw;
    DatePickerDialog picker;
    AutoCompleteTextView plantOrSeed;
    String Dest;


//   public void getData(String query){
//       Rx2AndroidNetworking.get(ApiEndpoint.GETTING_FLORA_API)
//               .addQueryParameter("query", query)
//               .build()
//               .getObjectObservable(AdminVO.class)
//               .subscribeOn(Schedulers.io())
//               .observeOn(AndroidSchedulers.mainThread())
//               .subscribe(new Observer<AdminVO>() {
//                   @Override
//                   public void onSubscribe(Disposable d) {
//
//                   }
//                   @Override
//                   public void onNext(AdminVO object) {
//                       Toast.makeText(harvestvisit.this, "Success", Toast.LENGTH_LONG).show();
//                       startActivity(new Intent(harvestvisit.this, harvestvisit.class));
//                   }
//
//                   @Override
//                   public void onError(Throwable e) {
//                       Toast.makeText(harvestvisit.this, "Invalid Username or Password", Toast.LENGTH_LONG).show();
//                       //hideLoading();
//                   }
//
//                   @Override
//                   public void onComplete() {
//                       //hideLoading();
//                   }
//               });
//   }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        String[] fruits = {"Apple","Apple1","Apple2","Apple3","Apple4","Apple5", "Banana", "Cherry", "Date", "Grape", "Kiwi", "Mango", "Pear"};

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harvestvisit);

            tvw             = (TextView)findViewById(R.id.textView1);
            plantOrSeed     = (AutoCompleteTextView)findViewById(R.id.autoCompletePlantsseed);
            harvestDate     = (EditText)findViewById(R.id.harvestdate);
            sowingDate      = (EditText)findViewById(R.id.sowingdate);
            sapQuantity     = (EditText)findViewById(R.id.sapplingquantity);
            harvestQuantity = (EditText)findViewById(R.id.harvestquantity);
            ownUse          = (EditText)findViewById(R.id.ownhomeuse);
            soldQuantity    = (EditText)findViewById(R.id.soldquantity);
            soldRate        = (EditText)findViewById(R.id.soldrate);
            totalIncome     = (EditText)findViewById(R.id.totalincome);
            submit          = (Button) findViewById(R.id.submit);
            submit.setOnClickListener(this);


        //Creating the instance of ArrayAdapter containing list of fruit names
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, fruits);
        //Getting the instance of AutoCompleteTextView

        plantOrSeed.setThreshold(1);//will start working from first character
        plantOrSeed.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        plantOrSeed.setTextColor(Color.RED);


        harvestDate.setInputType(InputType.TYPE_NULL);
        harvestDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerView datePickerView = new DatePickerView();
                datePickerView.setDatePickerView(HarvestVisitActivity.this,harvestDate);
            }
    });

        sowingDate.setInputType(InputType.TYPE_NULL);
        sowingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerView datePickerView = new DatePickerView();
                datePickerView.setDatePickerView(HarvestVisitActivity.this,sowingDate);
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
            //params.put("plantOrSeed",plantOrSeed.getText().toString());
            params.put("sowingDate", DateUtils.convertDateFormat(sowingDate.getText().toString()));
            params.put("sapQuantity",sapQuantity.getText().toString());
            params.put("harvestDate", DateUtils.convertDateFormat(harvestDate.getText().toString()));
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
                        Toast.makeText(HarvestVisitActivity.this, "Successfully Added", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(HarvestVisitActivity.this, " ", Toast.LENGTH_LONG).show();
                        hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        hideLoading();
                    }
                });
         }
    }


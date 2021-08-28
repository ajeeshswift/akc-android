package com.swift.akc.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.swift.akc.R;
import com.swift.akc.database.CommonUtil;
import com.swift.akc.database.DatabaseHelper;
import com.swift.akc.database.DatabaseUtil;
import com.swift.akc.extras.Storage;
import com.swift.akc.network.ApiEndpoint;
import com.swift.akc.network.data.AdminVO;
import com.swift.akc.network.data.FarmVillageListVO;
import com.swift.akc.network.data.HarvestForcastingVO;
import com.swift.akc.network.data.HarvestVO;
import com.swift.akc.network.data.PlantSeedListVO;
import com.swift.akc.utils.DateUtils;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class LoginActivity extends BaseAppCompatActivity implements View.OnClickListener {

    EditText username;

    EditText password;

    Button login;

    Context context;

    String strUsername, strPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_login);
        context = this;

        // shared perference - use to store small data
        // initialize

        CommonUtil.databaseUtil = new DatabaseUtil(context);
        CommonUtil.databaseUtil.open();
        CommonUtil.databaseHelper = new DatabaseHelper(context);
        CommonUtil.pref = context.getSharedPreferences(CommonUtil.MyPreferances,MODE_PRIVATE);

        username = findViewById(R.id.userName);
          username.setText("Admin");
        password = findViewById(R.id.password);
         password.setText("1955");
        login = findViewById(R.id.login);
        login.setOnClickListener(this);

        strUsername = CommonUtil.pref.getString("UserName","0");
        strPassword = CommonUtil.pref.getString("Password","0");

        if(CommonUtil.isOnline(context)){
            getFarmDetails();
             getPlantDaetils();

             // sending offlinne data to the server

            SendHarvestVisitEntry();
            sendForcastingEntry();

            Toast.makeText(context," Internet Connected Successfully",Toast.LENGTH_LONG).show();
        } else{
            Toast.makeText(context,"No Internet Connection",Toast.LENGTH_LONG).show();

        }

    }

    private void sendForcastingEntry() {

        Cursor cur =  CommonUtil.databaseUtil.getHarvestForcastingbystatus();

        if (cur.moveToFirst()) {
            try {
                do {
                    String farm = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.FORECAST_FARM_ID));
                    String forcastArea = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.FORECAST_CULTIVATION));
                    int plant = cur.getInt(cur.getColumnIndexOrThrow(DatabaseHelper.FORECAST_PLANT_SEED));
                    String date = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.FORECAST_ENTRY_DATE));
                    String time = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.FORECAST_ENTRY_TIME));
                    String cropDate = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.FORECAST_SOWING_DATE));
                    String seeds = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.FORECAST_SOWING_KG));

                    JSONObject params = new JSONObject();
                    try {
                        params.put("plantId",plant);
                        params.put("seeds", seeds);
                        params.put("area", forcastArea);
                        params.put("cropShowingDate", DateUtils.convertDateFormat(cropDate));
                        params.put("farmId", farm);
                        params.put("date", date);
                        params.put("time", time);

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
                                      CommonUtil.databaseUtil.updateHarvestForecasting(plant,"1");

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {
                                    hideLoading();
                                }
                            });




                } while (cur.moveToNext());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }


    }

    private void SendHarvestVisitEntry() {


        Cursor cur = CommonUtil.databaseUtil.getHarvestVisitListbyStatus();

        if (cur.moveToFirst()) {

            try {
                do {
                    String florastid = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.FARM_ID));
                    int plantseed = cur.getInt(cur.getColumnIndexOrThrow(DatabaseHelper.PLANTSEED));
                    String sowing_date = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.SOWING_DATE));
                    String slapping_qty = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.SAPPLING_QUANTITY));
                    String harvest_date = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.HARVEST_DATE));
                    String harvest_qty = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.HARVEST_QUANTITY));
                    String home_use = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.OWN_HOME_USE));
                    String sold_qty = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.SOLD_QUANTITY));
                    String sold_rate = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.SOLD_RATE));
                    String total_income = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.TOTAL_INCOME));
                    String status = cur.getString(cur.getColumnIndexOrThrow(DatabaseHelper.STATUS));

                    JSONObject params = new JSONObject();

                    try {
                        params.put("floraId", plantseed);
                        params.put("sowingDate", sowing_date);
                        params.put("sapQuantity", slapping_qty);
                        params.put("harvestDate",harvest_date);
                        params.put("harvestQuantity", harvest_qty);
                        params.put("ownUseQuantity", home_use);
                        params.put("soldQuantity", sold_qty);
                        params.put("soldRate", sold_rate);
                        params.put("totalIncome", total_income);
                        params.put("farmId", florastid);
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
                                    CommonUtil.databaseUtil.updateHarevest(plantseed);

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
                } while (cur.moveToNext());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }




    }

    private void getPlantDaetils() {

        Rx2AndroidNetworking.get(ApiEndpoint.FLORA_AUTOCOMPLETE_API)
                .addQueryParameter("query", "'")
                .build()
                .getObjectObservable(PlantSeedListVO.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PlantSeedListVO>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PlantSeedListVO plantSeedListVO) {

                        for(int i=0; i <plantSeedListVO.getData().size(); i++){
                            int strPlantId           = plantSeedListVO.getData().get(i).getId();
                            String strPlantName      = plantSeedListVO.getData().get(i).getFloraName();


                            if(!CommonUtil.databaseUtil.hasPlantVD(strPlantId)) {
                                CommonUtil.databaseUtil.addPlantDetails(strPlantId, strPlantName);
                            }
                        }

                       // mAdapter.refresh(plantSeedListVO.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        showApiError(e);
                    }

                    @Override
                    public void onComplete() {
                        hideLoading();
                    }
                });
    }


    private void getFarmDetails() {
        Log.e("Inside API","yes");

//        ApiInterface service = RetrofitClinetInstance.getRetrofitInstance().create(ApiInterface.class);
//        Call<ResponseBody> call = service.getFarmvillage();
//
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Log.e("API",""+response.toString());
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.e("API Error",""+t.toString());
//
//            }
//        });




        Rx2AndroidNetworking.get(ApiEndpoint.FARMER_VILLAGE_DETAILS_API)
                .build()
                .getObjectObservable(FarmVillageListVO.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FarmVillageListVO>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(FarmVillageListVO farmVillageListVO) {


                        for(int i=0; i <farmVillageListVO.getData().size(); i++){
                            int strFarmDetailId      = farmVillageListVO.getData().get(i).farmDetailId();
                            String strVillageName    = farmVillageListVO.getData().get(i).getVillageName();
                            int strVillageId         = farmVillageListVO.getData().get(i).getVillageId();
                            String strFarmno         = farmVillageListVO.getData().get(i).getFarmNo();
                            int strFarmId            = farmVillageListVO.getData().get(i).getFarmId();
                            String strFarmName       = farmVillageListVO.getData().get(i).getFarmName();

                            Log.e("Testing",""+ strFarmName);



                            if(!CommonUtil.databaseUtil.hasFarmVD(strFarmId,strFarmDetailId)) {
                                CommonUtil.databaseUtil.addFarmVillageDetails(strVillageName, strVillageId,
                                        strFarmno, strFarmId, strFarmName,strFarmDetailId);
                                Log.e("Testing 1",""+ strFarmName);


                            } else{
                                Log.e("Testing 2",""+ strFarmName);

                            }


                        }

                      //  mAdapter.refresh(plantSeedListVO.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Inside Error","error" +e);

                    }

                    @Override
                    public void onComplete() {
                        hideLoading();
                    }
                });
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.login) {
            // check internet connection
            if(CommonUtil.isOnline(context)) {

                SharedPreferences.Editor sharedEditor = CommonUtil.pref.edit();
                sharedEditor.putBoolean("NetworkCon",true);
                sharedEditor.apply();

                Log.e("Testing","Yes");  //System.out.println
                loginApiCall();
                //Toast.makeText(context,"yes internet Connection",Toast.LENGTH_SHORT).show(); //system message

            } else{
                Log.e("Testing","NO");
                //adding the value in shared preference
                SharedPreferences.Editor sharedEditor = CommonUtil.pref.edit();
                sharedEditor.putBoolean("NetworkCon",false);
                sharedEditor.apply();

                String sm = username.getText().toString().trim();
                String pw = password.getText().toString().trim();



                if(sm.equals(strUsername)&&pw.equals(strPassword)){
                     goToLandingPageActivity();

                } else{
                    Toast.makeText(context,"Invalid Username or Password.",Toast.LENGTH_SHORT).show();

                }

           //     Toast.makeText(context,"No internet Connection",Toast.LENGTH_SHORT).show();
            }
            //goToLandingPageActivity();
        }
    }


    private void loginApiCall() {
        Log.e("Testing","inside api");
        JSONObject params = new JSONObject();
        try {
            params.put("userName", username.getText().toString());
            params.put("password", password.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        showLoading();
        Rx2AndroidNetworking.post(ApiEndpoint.LOGIN_API)
                .addJSONObjectBody(params)
                .build()
                .getObjectObservable(AdminVO.class)
                .subscribeOn(Schedulers.io())
                .timeout(30, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AdminVO>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(AdminVO object) {
                        SharedPreferences.Editor sharedEditor = CommonUtil.pref.edit();
                        sharedEditor.putString("UserName", username.getText().toString().trim());
                        sharedEditor.putString("Password",password.getText().toString().trim());
                        sharedEditor.apply();

                        Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_LONG).show();
                        Storage.save(getApplicationContext(), object);
                        goToLandingPageActivity();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("testing",""+e);
                        Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_LONG).show();
                        hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        hideLoading();
                    }
            });
    }

    public void goToLandingPageActivity() {
        Intent intent = new Intent(this, LandingPageActivity.class);
        startActivity(intent);
    }
}

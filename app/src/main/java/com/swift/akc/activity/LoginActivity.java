package com.swift.akc.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.swift.akc.network.data.HarvestVisitListVO;
import com.swift.akc.network.data.PlantSeedListVO;
import com.swift.akc.network.retrofit.ApiInterface;
import com.swift.akc.network.retrofit.RetrofitClinetInstance;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    //    username.setText("Admin");
        password = findViewById(R.id.password);
   //     password.setText("1955");
        login = findViewById(R.id.login);
        login.setOnClickListener(this);

        strUsername = CommonUtil.pref.getString("UserName","0");
        strPassword = CommonUtil.pref.getString("Password","0");

        if(CommonUtil.isOnline(context)){
            getFarmDetails();
            getPlantDaetils();
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

                            Log.e("From Server","" + i+ strPlantId +" FNo" + strPlantName);


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

                            Log.e("From Server","" + i+ strVillageName +" FNo" + strFarmno +" ID" + strFarmId + strFarmName);


                            if(!CommonUtil.databaseUtil.hasFarmVD(strFarmId,strFarmDetailId)) {
                                CommonUtil.databaseUtil.addFarmVillageDetails(strVillageName, strVillageId,
                                        strFarmno, strFarmId, strFarmName,strFarmDetailId);

                                Log.e("Edgar","Added");

                            } else{
                                Log.e("Edgar","not Added");

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
            if(!CommonUtil.isOnline(context)) {

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

                Log.e("Testing"," "+ sm + "dshk "+ pw + "Us"+ strUsername  + "Pwd "+ strPassword);


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

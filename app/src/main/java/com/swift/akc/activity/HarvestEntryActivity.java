package com.swift.akc.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.swift.akc.R;
import com.swift.akc.network.ApiEndpoint;
import com.swift.akc.network.data.HarvestVO;

import org.json.JSONObject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HarvestEntryActivity extends LandingPageActivity implements View.OnClickListener {
    EditText farmno;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harvestentry);

        farmno    = (EditText)findViewById(R.id.farmno);
    }

    @Override
    public void onClick(View view){
        if(view.getId() == R.id.submit){
            harvestVisitEntryPageAPICall();
        }
    }
    private void harvestVisitEntryPageAPICall(){
        JSONObject params = new JSONObject();
        try{
            params.put("farmNo",farmno.getText().toString());
        }
        catch (Exception e){
            e.printStackTrace();
        }

        Rx2AndroidNetworking.post(ApiEndpoint.FARMER_DETAILS_API)
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
                        Toast.makeText(HarvestEntryActivity.this, "Successfully Added", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(HarvestEntryActivity.this, " ", Toast.LENGTH_LONG).show();
                        hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        hideLoading();
                    }
                });
    }
}
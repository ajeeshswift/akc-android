package com.swift.akc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.swift.akc.R;
import com.swift.akc.extras.Storage;
import com.swift.akc.network.ApiEndpoint;
import com.swift.akc.network.data.AdminVO;
import org.json.JSONObject;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseAppCompatActivity implements View.OnClickListener {

    EditText username;

    EditText password;

    Button login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_login);
        username = findViewById(R.id.userName);
        username.setText("Admin");
        password = findViewById(R.id.password);
        password.setText("1955");
        login = findViewById(R.id.login);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.login) {
            //loginApiCall();
            goToLandingPageActivity();
        }
    }


    private void loginApiCall() {
        JSONObject params = new JSONObject();
        try {
            params.put("userName", username.getText().toString());
            params.put("password", password.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Rx2AndroidNetworking.post(ApiEndpoint.LOGIN_API)
                .addJSONObjectBody(params)
                .build()
                .getObjectObservable(AdminVO.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AdminVO>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(AdminVO object) {
                        Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_LONG).show();
                        Storage.save(getApplicationContext(), object);
                        goToLandingPageActivity();
                    }

                    @Override
                    public void onError(Throwable e) {
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

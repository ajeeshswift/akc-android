package com.swift.akc;

import android.app.Application;
import com.androidnetworking.AndroidNetworking;
import com.swift.akc.interceptor.HttpRequestInterceptor;
import okhttp3.OkHttpClient;


public class AkcApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpClient okHttpClient = new OkHttpClient() .newBuilder()
                .addInterceptor(new HttpRequestInterceptor())
                .build();
        AndroidNetworking.initialize(getApplicationContext(), okHttpClient);
        AndroidNetworking.enableLogging();
    }
}

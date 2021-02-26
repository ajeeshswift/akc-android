package com.swift.akc;

import android.app.Application;
import com.androidnetworking.AndroidNetworking;
import com.swift.akc.interceptor.HttpRequestInterceptor;
import okhttp3.OkHttpClient;


public class AkcApplication extends Application {
    @Override
    public void onCreate() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());
            httpClient.addInterceptor(new HttpRequestInterceptor());
    }
}

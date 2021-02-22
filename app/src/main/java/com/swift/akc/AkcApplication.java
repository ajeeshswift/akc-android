package com.swift.akc;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;


public class AkcApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());
    }
}

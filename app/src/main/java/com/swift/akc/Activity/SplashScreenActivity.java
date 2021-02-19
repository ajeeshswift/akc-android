package com.swift.akc.Activity;


import android.os.Bundle;

import com.swift.akc.BaseAppCompactActivity;
import com.swift.akc.R;

public class SplashScreenActivity extends BaseAppCompactActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_splash);
    }
}
package com.swift.akc.activity;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.swift.akc.R;

public class MainActivity extends LandingPageActivity {
    private ConstraintLayout c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        c=findViewById(R.id.c);
    }
}

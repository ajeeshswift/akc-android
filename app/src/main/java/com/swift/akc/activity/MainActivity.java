package com.swift.akc.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

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

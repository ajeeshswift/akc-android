package com.swift.akc.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.swift.akc.LandingPageCompactActivity;
import com.swift.akc.R;

public class MainActivity extends LandingPageCompactActivity {
    private ConstraintLayout c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        c=findViewById(R.id.c);
    }
}

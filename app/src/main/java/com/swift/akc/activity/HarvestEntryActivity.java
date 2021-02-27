package com.swift.akc.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.swift.akc.R;

public class HarvestEntryActivity extends LandingPageActivity {
    EditText villagename;
    EditText farmno;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harvestentry);
    }
}
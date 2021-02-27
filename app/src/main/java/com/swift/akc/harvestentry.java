package com.swift.akc;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class harvestentry extends LandingPageCompactActivity {
    EditText villagename;
    EditText farmno;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harvestentry);
    }
}
package com.swift.akc.extras;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.swift.akc.R;

public class UIValidation extends AppCompatActivity {
    public UIValidation() {

    }

    public void validateFields(Activity activity, EditText fieldName,String value, String message) {
        if (value.equals("NULL") || value.equals("")) {
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
            fieldName.requestFocus();
            return;
        }
    }
}

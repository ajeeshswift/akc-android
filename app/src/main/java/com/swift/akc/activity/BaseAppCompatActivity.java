package com.swift.akc.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.error.ANError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.swift.akc.R;
import com.swift.akc.beans.ErrorInfoBean;
import com.swift.akc.extras.Storage;

import org.json.JSONObject;

public class BaseAppCompatActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showLoading() {
        hideLoading();
        mProgressDialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
        mProgressDialog.show();
        if (mProgressDialog.getWindow() != null) {
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        mProgressDialog.setContentView(R.layout.progress_dialog);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    public void showApiError(Throwable throwable) {
        if(throwable instanceof ANError) {
            ANError anError = (ANError) throwable;
            if(anError.getErrorCode() != 0) {
                String errorBody = anError.getErrorBody();
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                ErrorInfoBean errorInfoBean = gson.fromJson(errorBody, ErrorInfoBean.class);
                showErrorDialog(errorInfoBean.getMessage());
            } else {
                showErrorDialog(anError.getErrorDetail());
            }
        } else {
            showErrorDialog(throwable.getMessage());
        }
    }

    public void showErrorDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert");
        builder.setMessage(message);
        builder.setPositiveButton(R.string.alert_btn_ok, (dialog, which) -> {
            dialog.dismiss();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }
}

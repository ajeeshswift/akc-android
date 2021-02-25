package com.swift.akc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.swift.akc.activity.LoginActivity;
import com.swift.akc.activity.MainActivity;

public class LandingPageCompactActivity extends AppCompatActivity {
    private ProgressDialog mProgressDialog;

    public Animation blinkAnimation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        blinkAnimation =
                AnimationUtils.loadAnimation(this,
                        R.anim.blink);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.harvestentry:
                startActivity(new Intent(LandingPageCompactActivity.this, harvestentry.class));
                break;
            case R.id.harvestvisit:
                startActivity(new Intent(LandingPageCompactActivity.this, harvestvisit.class));
                break;
            case R.id.harvestforcasting:
                startActivity(new Intent(LandingPageCompactActivity.this, harvestingforcast.class));
                break;
        }

        return super.onOptionsItemSelected(item);
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
}

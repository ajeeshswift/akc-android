package com.swift.akc.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.swift.akc.R;
import com.swift.akc.fragments.ComingSoonFragment;
import com.swift.akc.fragments.HarvestVisitFragment;
import com.swift.akc.fragments.HomeFragment;

public class LandingPageActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private ProgressDialog mProgressDialog;

    private static final int FRAGMENT_HOME = 1;

    private static final int FRAGMENT_HARVEST_ENTRY = 2;

    Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_landing_page);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(this);
        displayView(FRAGMENT_HOME, "Home", true);
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
                startActivity(new Intent(LandingPageActivity.this, HarvestEntryActivity.class));
                break;
            case R.id.harvestvisit:
                startActivity(new Intent(LandingPageActivity.this, HarvestVisitActivity.class));
                break;
            case R.id.harvestforcasting:
                startActivity(new Intent(LandingPageActivity.this, HarvestingForcastActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment;
        switch (item.getItemId()) {
            case R.id.algorithm:
                displayView(FRAGMENT_HARVEST_ENTRY, "Harvest Entry", true);
                break;
            case R.id.course:
                displayView(FRAGMENT_HARVEST_ENTRY, "Harvest Entry", true);
                break;
            case R.id.profile:
                displayView(FRAGMENT_HARVEST_ENTRY, "Harvest Entry", true);
                break;
        }
        return true;
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

    public void displayView(int position, String aTitle, boolean addToBackstack) {
        Fragment fragment;
        switch (position) {
            case FRAGMENT_HOME:
                fragment = HomeFragment.newInstance(aTitle);
                break;
            case FRAGMENT_HARVEST_ENTRY:
                fragment = HarvestVisitFragment.newInstance(aTitle);
                break;
            default:
                fragment = ComingSoonFragment.newInstance(aTitle);
                break;
        }
        switchFragment(fragment, addToBackstack);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
            confirmBeforeExit(getString(R.string.confirm_dialog_title),
                    getString(R.string.exit_message_home_page));
        } else {
            super.onBackPressed();
        }
    }

    private void confirmBeforeExit(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.alert_btn_ok, (dialog, which) -> {
            dialog.dismiss();
            finish();
        });
        builder.setNegativeButton(R.string.alert_btn_cancel, (dialog, which) -> displayView(FRAGMENT_HOME, "Home", false));
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    public void switchFragment(Fragment fragment, boolean aAddtoBackstack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        String backStateName = ft.getClass().getName();
        //ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        ft.replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName());
        if (aAddtoBackstack)
            ft.addToBackStack(backStateName);
        ft.commit();
    }
}

package com.swift.akc.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.swift.akc.R;
import com.swift.akc.extras.EntryType;
import com.swift.akc.extras.Storage;
import com.swift.akc.fragments.ComingSoonFragment;
import com.swift.akc.fragments.HarvestFarmSearchFragment;
import com.swift.akc.fragments.HarvestFilterFragment;
import com.swift.akc.fragments.HarvestForecastingEntryFragment;
import com.swift.akc.fragments.HarvestForecastingListFragment;
import com.swift.akc.fragments.HarvestVisitEntryFragment;
import com.swift.akc.fragments.HarvestVisitListFragment;
import com.swift.akc.fragments.HomeFragment;
import com.swift.akc.fragments.ProfileFragment;

public class LandingPageActivity extends BaseAppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private ProgressDialog mProgressDialog;

    private static final int FRAGMENT_HOME = 1;

    public static final int FRAGMENT_HARVEST_FARM_SEARCH = 2;

    public static final int FRAGMENT_HARVEST_VISIT_ENTRY = 3;

    public static final int FRAGMENT_HARVEST_FORECASTING_ENTRY = 4;

    public static final int FRAGMENT_HARVEST_VISIT_LIST = 5;

    public static final int FRAGMENT_PROFILE = 6;

    public static final int FRAGMENT_HARVEST_FILTER = 7;

    public static final int FRAGMENT_HARVEST_FORCASTING_LIST = 8;

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
        //displayView(FRAGMENT_HARVEST_VISIT_LIST, "Home", true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ic_home:
                displayView(FRAGMENT_HOME, "Home", true);
                break;
            case R.id.algorithm:
                Storage.selectedMenu = EntryType.HARVEST_VISIT_ENTRY;
                displayView(FRAGMENT_HARVEST_FARM_SEARCH, "Harvest Entry", true);
                break;
            case R.id.course:
                Storage.selectedMenu = EntryType.HARVEST_FORECASTING_ENTRY;
                displayView(FRAGMENT_HARVEST_FARM_SEARCH, "Harvest Entry", true);
                break;
//            case R.id.harvestlist:
//                Storage.selectedMenu = EntryType.HARVEST_VISIT_LIST;
//                displayView(FRAGMENT_HARVEST_VISIT_LIST, "Harvest Entry", true);
//                break;
//            case R.id.harvestForcastinglist:
//                Storage.selectedMenu = EntryType.HARVEST_FORCASTING_LIST;
//                displayView(FRAGMENT_HARVEST_FORCASTING_LIST, "Harvest Entry", true);
//                break;
            case R.id.profile:
                Storage.selectedMenu = EntryType.HARVEST_PROFILE;
                displayView(FRAGMENT_PROFILE, "Harvest Entry", true);
                break;
        }
        return true;
    }

    public void displayView(int position, String aTitle, boolean addToBackstack) {
        Fragment fragment;
        switch (position) {
            case FRAGMENT_HOME:
                fragment = HomeFragment.newInstance(aTitle);
                break;
            case FRAGMENT_HARVEST_FARM_SEARCH:
                fragment = HarvestFarmSearchFragment.newInstance(aTitle);
                break;
            case FRAGMENT_HARVEST_FORECASTING_ENTRY:
                fragment = HarvestForecastingEntryFragment.newInstance(aTitle);
                break;
            case FRAGMENT_HARVEST_VISIT_ENTRY:
                fragment = HarvestVisitEntryFragment.newInstance(aTitle);
                break;
            case FRAGMENT_HARVEST_VISIT_LIST:
                fragment = HarvestVisitListFragment.newInstance(aTitle);
                break;
            case FRAGMENT_HARVEST_FORCASTING_LIST:
                fragment = HarvestForecastingListFragment.newInstance(aTitle);
                break;
            case FRAGMENT_PROFILE:
                fragment = ProfileFragment.newInstance(aTitle);
                break;
            case FRAGMENT_HARVEST_FILTER:
                fragment = HarvestFilterFragment.newInstance(aTitle);
                break;
            default :
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

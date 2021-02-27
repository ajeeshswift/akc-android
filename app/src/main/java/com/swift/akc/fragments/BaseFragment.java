package com.swift.akc.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.swift.akc.activity.BaseAppCompatActivity;
import com.swift.akc.activity.LandingPageActivity;
import com.swift.akc.extras.Constants;

public class BaseFragment extends Fragment {

    private static final String TAG = "";
    protected View mParentView;
    private String mTitle = "MyTitle";
    protected String mSubTitle = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null) {
            mTitle = bundle.getString(Constants.INTENT_PARAM_TITLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return mParentView;
    }

    public void switchFragment(int fragment, String title, boolean addToBackStack) {
        LandingPageActivity landingPageActivity = (LandingPageActivity) getActivity();
        if(null != landingPageActivity) {
            landingPageActivity.displayView(fragment, title, addToBackStack);
        }
    }

    public void showLoading() {
        BaseAppCompatActivity appCompatActivity = (BaseAppCompatActivity) getActivity();
        if(appCompatActivity != null) {
            appCompatActivity.showLoading();
        }
    }


    public void hideLoading() {
        BaseAppCompatActivity appCompatActivity = (BaseAppCompatActivity) getActivity();
        if(appCompatActivity != null) {
            appCompatActivity.hideLoading();
        }
    }

    public void showApiError(Throwable e) {
        BaseAppCompatActivity appCompatActivity = (BaseAppCompatActivity) getActivity();
        if(appCompatActivity != null) {
            appCompatActivity.showApiError(e);
        }
    }
}

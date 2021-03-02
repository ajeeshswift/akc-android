package com.swift.akc.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.swift.akc.R;
import com.swift.akc.activity.MainActivity;
import com.swift.akc.extras.Constants;
import com.swift.akc.extras.Storage;

public class ProfileFragment extends BaseFragment implements View.OnClickListener {
    Button submit;

    public ProfileFragment(){

    }

    public static ProfileFragment newInstance(String title) {
        ProfileFragment profileFragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.INTENT_PARAM_TITLE, title);
        profileFragment.setArguments(bundle);
        return profileFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mParentView = inflater.inflate(R.layout.fr_profile, container, false);
        return mParentView;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.submit) {
//            startActivity(new Intent(this, MainActivity.class));
//            Storage.clear(Context);
        }
    }

}

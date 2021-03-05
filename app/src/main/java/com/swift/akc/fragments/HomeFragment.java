package com.swift.akc.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.swift.akc.R;
import com.swift.akc.activity.LandingPageActivity;
import com.swift.akc.extras.Constants;

public class HomeFragment extends BaseFragment implements View.OnClickListener{
    CardView harvestVisit,harvestForcast;


    public HomeFragment() {

    }

    public static HomeFragment newInstance(String title) {
        HomeFragment comingSoonFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.INTENT_PARAM_TITLE, title);
        comingSoonFragment.setArguments(bundle);
        return comingSoonFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mParentView = inflater.inflate(R.layout.fr_home, container, false);

        harvestVisit = (CardView) mParentView.findViewById(R.id.harvestVisit);
        harvestForcast = (CardView) mParentView.findViewById(R.id.harvestForcasting);

        harvestVisit.setOnClickListener(this);
        harvestForcast.setOnClickListener(this);
        return mParentView;
    }
    String aTittle;
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.harvestVisit) {
            switchFragment(LandingPageActivity.FRAGMENT_HARVEST_VISIT_LIST,"",true);
        }

        if (view.getId() == R.id.harvestForcasting) {
            switchFragment(LandingPageActivity.FRAGMENT_HARVEST_FORCASTING_LIST,"",true);
        }
    }
}

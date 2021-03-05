package com.swift.akc.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.swift.akc.R;
import com.swift.akc.activity.LandingPageActivity;
import com.swift.akc.adapters.HarvestVisitListAdapter;
import com.swift.akc.extras.Constants;
import com.swift.akc.network.ApiEndpoint;
import com.swift.akc.network.data.HarvestVisitListVO;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HarvestVisitListFragment extends BaseFragment {

    private HarvestVisitListAdapter mAdapter;

    private RecyclerView mRecyclerView;

    public HarvestVisitListFragment() {

    }

    public static HarvestVisitListFragment newInstance(String title) {
        HarvestVisitListFragment comingSoonFragment = new HarvestVisitListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.INTENT_PARAM_TITLE, title);
        comingSoonFragment.setArguments(bundle);
        return comingSoonFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mParentView = inflater.inflate(R.layout.item_harvest_list, container, false);
        mRecyclerView = mParentView.findViewById(R.id.recycler_view);
        return mParentView;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.filter_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.icon_filter) {
            switchFragment(LandingPageActivity.FRAGMENT_HARVEST_FILTER, "Filter", true);
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Nullable
    public void onViewCreated(@NonNull View view, @io.reactivex.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new HarvestVisitListAdapter(getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
        getHarvestList();
    }

    public void getHarvestList() {
        showLoading();
        Rx2AndroidNetworking
                .get(ApiEndpoint.HARVEST_VISIT_LIST_API)
                .build()
                .getObjectObservable(HarvestVisitListVO.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HarvestVisitListVO>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HarvestVisitListVO listVO) {
                        mAdapter.refresh(listVO.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        hideLoading();
                    }
                });

    }
}

package com.swift.akc.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.swift.akc.R;
import com.swift.akc.adapters.HarvestVisitAdapter;
import com.swift.akc.extras.Constants;
import com.swift.akc.network.ApiEndpoint;
import com.swift.akc.network.data.HarvestVisitListVO;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HarvestVisitListFragment extends BaseFragment {

    private HarvestVisitAdapter mAdapter;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mParentView = inflater.inflate(R.layout.fr_harvest_visit, container, false);
        mRecyclerView = mParentView.findViewById(R.id.recycler_view);
        return mParentView;
    }

    @NonNull
    @Nullable
    public void onViewCreated(@NonNull View view, @io.reactivex.annotations.Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        mAdapter = new HarvestVisitAdapter(getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
        getHarvestList();
    }

    public void getHarvestList(){
        showLoading();
        Rx2AndroidNetworking
                .get(ApiEndpoint.HARVEST_API)
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

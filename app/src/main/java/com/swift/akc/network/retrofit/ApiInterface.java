package com.swift.akc.network.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("harvest/farmVillageList")
    public Call<ResponseBody> getFarmvillage();
}

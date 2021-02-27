package com.swift.akc.interceptor;

import com.swift.akc.extras.Storage;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HttpRequestInterceptor implements Interceptor {

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request().newBuilder();
        if(Storage.TOKEN == null || Storage.TOKEN.equals("")) {
            return chain.proceed(requestBuilder.build());
        }
        requestBuilder.addHeader("Authorization", Storage.TOKEN);
        return chain.proceed(requestBuilder.build());
    }
}

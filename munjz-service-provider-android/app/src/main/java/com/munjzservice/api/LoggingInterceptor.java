package com.munjzservice.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by innoppl on 11/05/17.
 */

 class LoggingInterceptor implements Interceptor {

    private String requestUrl;

     String getRequestUrl() {
        return requestUrl;
    }

     void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        setRequestUrl(response.request().url().toString());
        return response;

    }
}

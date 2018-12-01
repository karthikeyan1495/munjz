package com.munjzservice.api;

import com.munjzservice.MyApp;
import com.munjzservice.R;
import com.munjzservice.sharedpreferences.AppSession;

import java.io.IOException;

import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mac on 12/11/17.
 */

public class APIConfiguration {
    private static final APIConfiguration ourInstance = new APIConfiguration();

    public static APIConfiguration getInstance() {
        return ourInstance;
    }

    private APIConfiguration() {
    }


    public <S> S createService(Class<S> serviceClass) {

         HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
         //OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        OkHttpClient.Builder httpClient = SelfSigningClientBuilder.createClient();

        Retrofit.Builder builder = new Retrofit.Builder();

        //httpClient.followRedirects(true);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder();
                requestBuilder.header("Content-Type", "application/json");
                requestBuilder.header("Accept", "application/json");
                requestBuilder.header("language", AppSession.getInstance(MyApp.getContext()).getLanguageKey());
                requestBuilder.method(original.method(), original.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
       // httpClient.interceptors().add(new LoggingInterceptor());


        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        // add logging as last interceptor
        httpClient.addInterceptor(logging);
        Retrofit retrofit = builder.baseUrl(MyApp.getContext().getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(httpClient.build())
                .build();
        // do something for a debug build
        return retrofit.create(serviceClass);
    }
}

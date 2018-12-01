package com.munjzservice.utility;

import android.util.Log;

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mac on 11/13/17.
 */

public class InternetReachability {

    private boolean isReachable=false;

    private static final InternetReachability ourInstance = new InternetReachability();

    public static InternetReachability getInstance() {
        return ourInstance;
    }

    private InternetReachability() {

    }

    public void init(){
        ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responses -> {
                    Log.d("reachable",""+responses);
                    isReachable=responses;
                });
    }

    public boolean isReachable(){
        return isReachable;
    }
}

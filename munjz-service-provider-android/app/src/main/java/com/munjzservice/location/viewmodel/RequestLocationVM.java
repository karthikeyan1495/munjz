package com.munjzservice.location.viewmodel;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by mac on 12/26/17.
 */

public class RequestLocationVM {

    Activity activity;

    public RequestLocationVM(@NonNull Activity activity){
        this.activity=activity;
    }

    public void onClickBack(View view){
        activity.finish();
    }

}

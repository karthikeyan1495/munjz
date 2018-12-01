package com.munjzservice.servicerequest.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by mac on 2/13/18.
 */

public class ImageViewVM {

    Activity activity;

    public ImageViewVM(@NonNull Activity activity){
        this.activity=activity;
    }

    public void onClickBack(View view){
        activity.finish();
    }

}

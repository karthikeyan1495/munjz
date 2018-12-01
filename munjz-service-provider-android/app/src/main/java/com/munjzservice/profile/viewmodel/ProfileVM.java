package com.munjzservice.profile.viewmodel;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by mac on 12/12/17.
 */

public class ProfileVM {

    Activity activity;
    public ProfileVM(@NonNull Activity activity){
        this.activity=activity;
    }

    public void onClickBack(View view){
        activity.finish();
    }

}

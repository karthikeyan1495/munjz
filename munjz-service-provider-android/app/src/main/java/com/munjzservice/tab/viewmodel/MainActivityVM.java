package com.munjzservice.tab.viewmodel;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.munjzservice.R;
import com.munjzservice.balance.BalanceActivity;
import com.munjzservice.login.LoginActivity;
import com.munjzservice.profile.ProfileActivity;
import com.munjzservice.servicerequest.ViewServiceRequestActivity;
import com.munjzservice.settings.SettingsActivity;
import com.munjzservice.sharedpreferences.AppSession;

/**
 * Created by mac on 12/11/17.
 */

public class MainActivityVM {

    Activity activity;

    public MainActivityVM(@NonNull Activity activity){
        this.activity=activity;
    }

    public void onClickLogout(View view){
        logout();
    }

    public void onClickProfile(View view){
        activity.startActivity(new Intent(activity, ProfileActivity.class));
        //activity.startActivity(new Intent(activity, ViewServiceRequestActivity.class));
    }

    public void onClickSetting(View view){
        activity.startActivity(new Intent(activity, SettingsActivity.class));
    }

    public void onClickBalance(View view){
        activity.startActivity(new Intent(activity, BalanceActivity.class));
    }

    private void logout() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setMessage(activity.getString(R.string.logout_alert));
        alertDialogBuilder.setPositiveButton(activity.getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        dialog.dismiss();
                        AppSession.getInstance(activity).saveLoginStatus(false);
                        //AppSession.getInstance(activity).clearUserData();
                        activity.startActivity(new Intent(activity, LoginActivity.class));
                        activity.finish();
                    }
                });

        alertDialogBuilder.setNegativeButton(activity.getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}

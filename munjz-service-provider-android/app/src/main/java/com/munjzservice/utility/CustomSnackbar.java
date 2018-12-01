package com.munjzservice.utility;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;

/**
 * Created by mac on 11/15/17.
 */

public class CustomSnackbar {

    private static final CustomSnackbar ourInstance = new CustomSnackbar();

    public static CustomSnackbar getInstance() {
        return ourInstance;
    }

    private CustomSnackbar() {
    }

    public void showSnackbar(Activity activity, String message,boolean isSuccess){
        TSnackbar snackbar = TSnackbar.make(activity.findViewById(android.R.id.content), message, TSnackbar.LENGTH_LONG);
        snackbar.setMaxWidth(Utility.getInstance().getScreenWidth(activity));
        View snackbarView = snackbar.getView();
        if (isSuccess) {
            snackbarView.setBackgroundColor(Color.parseColor("#32CD32"));
        }else{
            snackbarView.setBackgroundColor(Color.parseColor("#FF0000"));
        }
        TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(17);
        snackbar.show();
    }

}

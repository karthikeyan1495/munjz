package com.munjzservice.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.munjzservice.sharedpreferences.AppSession;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by mac on 11/15/17.
 */

public class Utility {
    private static final Utility ourInstance = new Utility();

    public static Utility getInstance() {
        return ourInstance;
    }

    private Utility() {
    }

    public void openBrowser(Activity activity,String url){
        Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        activity.startActivity(intent);
    }

    public void hideKeyboard(Activity activity){
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public int getScreenWidth(Activity activity){
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return  metrics.widthPixels;
    }

    public Configuration setLanguage(Activity activity){
        Locale locale = new Locale(AppSession.getInstance(activity).getLanguageKey());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        activity.getBaseContext().getResources().updateConfiguration(config, activity.getBaseContext().getResources().getDisplayMetrics());
        return  config;
    }

    public long getTimeStamp(String date){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = (Date) formatter.parse(date);
            return date1.getTime();
        }catch (Exception e){
            e.printStackTrace();
        }
        return System.currentTimeMillis();
    }

    public void openCallDialPad(Activity activity,String number){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+number));
        activity.startActivity(intent);
    }

}

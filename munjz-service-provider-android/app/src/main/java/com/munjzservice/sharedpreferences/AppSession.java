package com.munjzservice.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.munjzservice.MyApp;
import com.munjzservice.R;
import com.munjzservice.profile.model.Customer;

/**
 * Created by mac on 11/15/17.
 */

public class AppSession extends SessionConstant{

    private final String FILE_NAME = "MunjzSeriveProvider-Preferences";

    private static AppSession AppSession;
    SharedPreferences preferences;

    private AppSession() {
    }

    public static AppSession getInstance(Context context) {
        if (AppSession == null) {
            AppSession = new AppSession();
        }
        AppSession.getPreferenceObject(context);
        return AppSession;
    }

    private void getPreferenceObject(Context context) {
        preferences = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
    }

    public void saveLoginStatus(boolean status){
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean(IS_LOGIN,status);
        editor.commit();
    }
    public boolean isLogin(){
        return preferences.getBoolean(IS_LOGIN,false);
    }


    public void saveCustomerInfo(Customer customer){
        Gson gson = new Gson();
        String json = gson.toJson(customer);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(USER_INFO, json);
        editor.commit();
    }

    public Customer getCustomerInfo(){
        String json = preferences.getString(USER_INFO, null);
        Gson gson = new Gson();
        return  gson.fromJson(json, Customer.class);
    }



    public void saveLanguageKey(String key){
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(LANGUAGE_KEY, key);
        editor.commit();
    }

    public String getLanguageKey(){
        return preferences.getString(LANGUAGE_KEY, MyApp.getContext().getString(R.string.ar));
    }

    public void saveBadgeCount(int count){
        SharedPreferences.Editor editor=preferences.edit();
        editor.putInt(BADGE_COUNT, count);
        editor.commit();
    }

    public int getBadgeCount(){
        return preferences.getInt(BADGE_COUNT, 0);
    }
    public void clearUserData() {
        preferences.edit().clear().commit();
    }
}

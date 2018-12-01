package com.munjzservice.api;

import android.app.Activity;

import com.munjzservice.R;
import com.munjzservice.utility.CustomSnackbar;

import org.json.JSONObject;

/**
 * Created by mac on 11/15/17.
 */

public class APIErrorHandler {
    private static final APIErrorHandler ourInstance = new APIErrorHandler();

    public static APIErrorHandler getInstance() {
        return ourInstance;
    }

    private APIErrorHandler() {
    }

    public void errorHandler(Activity activity, int status_code, String message){

        switch (status_code){
            case 200:
                CustomSnackbar.getInstance().showSnackbar(activity,message,true);
                break;
            case 400:
                CustomSnackbar.getInstance().showSnackbar(activity,messageParsing(activity,message),false);
                break;
            default:
                CustomSnackbar.getInstance().showSnackbar(activity,activity.getString(R.string.something_went_wrong),false);
                break;
        }
    }

    private String messageParsing(Activity activity, String message){
        try {
            JSONObject jsonObject=new JSONObject(message);
            if (jsonObject.has("msg")){
                return jsonObject.getString("msg");
            }
        }catch (Exception e){
            return activity.getString(R.string.something_went_wrong);
        }
        return activity.getString(R.string.something_went_wrong);
    }
}

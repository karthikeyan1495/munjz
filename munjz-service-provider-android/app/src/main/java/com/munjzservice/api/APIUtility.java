package com.munjzservice.api;


import com.google.firebase.iid.FirebaseInstanceId;
import com.munjzservice.login.model.LoginRequestModel;

/**
 * Created by mac on 11/15/17.
 */

public class APIUtility {
    private static final APIUtility ourInstance = new APIUtility();

    public static APIUtility getInstance() {
        return ourInstance;
    }

    private APIUtility() {
    }

    public LoginRequestModel genenrateLoginRequestModel(String userName, String password){
        LoginRequestModel loginRequestModel=new LoginRequestModel();
        loginRequestModel.setEmail(userName);
        loginRequestModel.setPassword(password);
        loginRequestModel.setDevice_token(FirebaseInstanceId.getInstance().getToken());
        loginRequestModel.setDevice_type("android");
        return loginRequestModel;
    }
}

package com.munjzservice.login.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.util.Patterns;
import android.view.View;

import com.munjzservice.R;
import com.munjzservice.api.APIConfiguration;
import com.munjzservice.api.APIErrorHandler;
import com.munjzservice.api.APIUtility;
import com.munjzservice.api.APICall;
import com.munjzservice.profile.model.Customer;
import com.munjzservice.login.model.UserInfo;
import com.munjzservice.sharedpreferences.AppSession;
import com.munjzservice.tab.MainActivity;
import com.munjzservice.utility.CustomSnackbar;
import com.munjzservice.utility.InternetReachability;
import com.munjzservice.utility.MyProgressDialog;
import com.munjzservice.utility.Utility;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by mac on 12/13/17.
 */

public class LoginVM {

    Activity activity;
    UserInfo userInfo;
    MyProgressDialog myProgressDialog;



    public LoginVM(@NonNull Activity activity,UserInfo userInfo){
        this.activity=activity;
        this.userInfo=userInfo;
        myProgressDialog=new MyProgressDialog();
    }

    public void onClickRootView(View view){
        Utility.getInstance().hideKeyboard(activity);
    }

    public void onClickForgotPassword(View view) {
        Utility.getInstance().openBrowser(activity,activity.getString(R.string.forgot_password_url));
        Utility.getInstance().hideKeyboard(activity);
    }

    public void onClickLogin(View view) {
        Utility.getInstance().hideKeyboard(activity);
        validation();
    }


    private void validation() {
        if (userInfo.getEmail().trim().length()==0||!Patterns.EMAIL_ADDRESS.matcher(userInfo.getEmail().trim()).matches()){
            CustomSnackbar.getInstance().showSnackbar(activity,activity.getString(R.string.email_error),false);
        }else if (userInfo.getPassword().length()==0){
            CustomSnackbar.getInstance().showSnackbar(activity,activity.getString(R.string.password_error),false);
        }else{
            loginServerCall(userInfo.getEmail().trim(),userInfo.getPassword());
        }
    }

    private void loginServerCall(String userName,String password){
       // "email":"jagannathan.p@kenhike.ae",
         //       "Password":"jack"
        if (InternetReachability.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService
                    (APICall.class);
            Observable<Response<Customer>> observable = api.customerLogin(APIUtility.getInstance().genenrateLoginRequestModel(userName, password));
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code()==200){
                            AppSession.getInstance(activity).saveLoginStatus(true);
                            AppSession.getInstance(activity).saveCustomerInfo(responses.body());
                            activity.startActivity(new Intent(activity, MainActivity.class));
                            activity.finish();
                        }else{
                            APIErrorHandler.getInstance().errorHandler(activity,responses.code(),responses.errorBody().string());
                        }
                    }, throwable -> {
                        myProgressDialog.dismissDialog();
                        CustomSnackbar.getInstance().showSnackbar(activity,activity.getString(R.string.something_went_wrong),false);
                    });
        }else {
            CustomSnackbar.getInstance().showSnackbar(activity,activity.getString(R.string.no_internet),false);
        }
    }

}

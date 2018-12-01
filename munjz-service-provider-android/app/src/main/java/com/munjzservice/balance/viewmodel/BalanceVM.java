package com.munjzservice.balance.viewmodel;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;

import com.munjzservice.R;
import com.munjzservice.api.APICall;
import com.munjzservice.api.APIConfiguration;
import com.munjzservice.api.APIErrorHandler;
import com.munjzservice.balance.model.BalanceResponse;
import com.munjzservice.sharedpreferences.AppSession;
import com.munjzservice.tab.Status;
import com.munjzservice.tab.active.model.RequestAction;
import com.munjzservice.tab.active.model.StatusChange;
import com.munjzservice.utility.CustomSnackbar;
import com.munjzservice.utility.InternetReachability;
import com.munjzservice.utility.MyProgressDialog;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by mac on 2/5/18.
 */

public class BalanceVM extends java.util.Observable{

    Activity activity;

    MyProgressDialog myProgressDialog;

    public BalanceVM(@NonNull Activity activity){
        this.activity=activity;
        myProgressDialog=new MyProgressDialog();
        balanceAPICall();
    }

    public void onClickBack(View view){
        activity.finish();
    }

    private void balanceAPICall(){
        if (InternetReachability.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService
                    (APICall.class);
            Observable<Response<BalanceResponse>> observable= api.balance(AppSession.getInstance(activity).getCustomerInfo().getUserid());
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code()==200){
                            BalanceResponse balanceResponse=responses.body();
                            setChanged();
                            notifyObservers(balanceResponse);
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

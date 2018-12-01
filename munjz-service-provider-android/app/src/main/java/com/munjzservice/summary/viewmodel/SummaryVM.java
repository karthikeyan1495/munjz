package com.munjzservice.summary.viewmodel;

import android.app.Activity;
import android.view.View;

import com.munjzservice.R;
import com.munjzservice.api.APICall;
import com.munjzservice.api.APIConfiguration;
import com.munjzservice.api.APIErrorHandler;
import com.munjzservice.sharedpreferences.AppSession;
import com.munjzservice.summary.model.Summary;
import com.munjzservice.tab.active.model.ActiveModel;
import com.munjzservice.tab.active.model.ServiceRequest;
import com.munjzservice.utility.CustomSnackbar;
import com.munjzservice.utility.InternetReachability;
import com.munjzservice.utility.MyProgressDialog;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by mac on 2/25/18.
 */

public class SummaryVM extends java.util.Observable{

    Activity activity;
    MyProgressDialog myProgressDialog;
    Summary summary;
    ServiceRequest serviceRequest;
    public SummaryVM(Activity activity,ServiceRequest serviceRequest){
        this.activity=activity;
        this.serviceRequest=serviceRequest;
        myProgressDialog=new MyProgressDialog();
        summaryAPICall();
    }

    public void onClickBack(View view){
        activity.finish();
    }

    private void summaryAPICall(){
        if (InternetReachability.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService
                    (APICall.class);
            Observable<Response<Summary>> observable = api.summary(serviceRequest.getId());
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code()==200){
                            summary=responses.body();
                            setChanged();
                            notifyObservers();
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

    public Summary getSummary(){
        return summary;
    }

}

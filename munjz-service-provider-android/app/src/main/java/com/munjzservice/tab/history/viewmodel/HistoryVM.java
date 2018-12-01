package com.munjzservice.tab.history.viewmodel;

import android.app.Activity;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import com.munjzservice.R;
import com.munjzservice.api.APICall;
import com.munjzservice.api.APIConfiguration;
import com.munjzservice.api.APIErrorHandler;
import com.munjzservice.sharedpreferences.AppSession;
import com.munjzservice.tab.active.model.ActiveModel;
import com.munjzservice.utility.CustomSnackbar;
import com.munjzservice.utility.InternetReachability;
import com.munjzservice.utility.MyProgressDialog;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by mac on 12/16/17.
 */

public class HistoryVM extends java.util.Observable{

    MyProgressDialog myProgressDialog;
    Activity activity;

    ActiveModel activeModel=new ActiveModel();
    public final ObservableBoolean isNoData = new ObservableBoolean(false);

    public HistoryVM(@NonNull Activity activity){
        this.activity=activity;
        myProgressDialog=new MyProgressDialog();
        isNoData.set(false);
    }
    public void callAPI(){
        isNoData.set(false);
        callRequestService(1);
    }

    public void loadMoreVideo(int page){
        callRequestService(page);
    }

    private void callRequestService(int page){
        if (InternetReachability.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService
                    (APICall.class);
            Observable<Response<ActiveModel>> observable = api.requestList(AppSession.getInstance(activity).getCustomerInfo().getUserid(),"historyservice",page);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code()==200){
                            activeModel=responses.body();
                            showNoDataMessage(page);
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

    public ActiveModel getActiveModel(){
        return activeModel;
    }

    private void showNoDataMessage(int page){
        if (page==1){
            if (activeModel.getList().size()==0){
                isNoData.set(true);
            }
        }
    }

}

package com.munjzservice.share.viewmodel;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;

import com.munjzservice.R;
import com.munjzservice.api.APICall;
import com.munjzservice.api.APIConfiguration;
import com.munjzservice.api.APIErrorHandler;
import com.munjzservice.share.model.EmailData;
import com.munjzservice.share.model.ShareRequest;
import com.munjzservice.share.model.ShareResponse;
import com.munjzservice.tab.active.model.ServiceRequest;
import com.munjzservice.tab.active.model.StatusChange;
import com.munjzservice.utility.CustomSnackbar;
import com.munjzservice.utility.InternetReachability;
import com.munjzservice.utility.MyProgressDialog;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by mac on 2/5/18.
 */

public class ShareVM {
    Activity activity;
    MyProgressDialog myProgressDialog;
    ServiceRequest serviceRequest;
    public ShareVM(@NonNull Activity activity,ServiceRequest serviceRequest){
        this.activity=activity;
        this.serviceRequest=serviceRequest;
        myProgressDialog=new MyProgressDialog();
    }
    public void onClickBack(View view){
        this.activity.finish();
    }

    public void onClickSend(View view, EmailData emailData){
        if (emailData.getEmails().trim().equals(""))
        {
            CustomSnackbar.getInstance().showSnackbar(activity,activity.getString(R.string.email_error),false);
        }else {
            shareAPICall(serviceRequest,emailData);
        }
    }
    private void shareAPICall(ServiceRequest serviceRequest,EmailData emailData){
        if (InternetReachability.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService
                    (APICall.class);
            Observable<Response<ShareResponse>> observable = api.share(generateShareRequest(serviceRequest,emailData));
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code()==200){
                            APIErrorHandler.getInstance().errorHandler(activity,responses.code(),responses.body().getStatus());
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

    private ShareRequest generateShareRequest(ServiceRequest serviceRequest,EmailData emailData){
        ShareRequest shareRequest=new ShareRequest();
        shareRequest.setTitle(serviceRequest.getTitle());
        shareRequest.setRequest_no(serviceRequest.getServiceid());
        shareRequest.setComments(emailData.getComments());
        List<String> list=new ArrayList<>();
        String [] array=emailData.getEmails().split("\\,");
        for (int i=0;i<array.length;i++){
            list.add(array[i]);
        }
        shareRequest.setEmail_ids(list);
        return shareRequest;
    }

}

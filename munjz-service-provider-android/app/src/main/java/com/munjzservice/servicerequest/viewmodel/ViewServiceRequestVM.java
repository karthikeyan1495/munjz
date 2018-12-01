package com.munjzservice.servicerequest.viewmodel;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.munjzservice.R;
import com.munjzservice.api.APICall;
import com.munjzservice.api.APIConfiguration;
import com.munjzservice.api.APIErrorHandler;
import com.munjzservice.location.RequestLocationActivity;
import com.munjzservice.servicerequest.ImageViewActivity;
import com.munjzservice.servicerequest.model.NotificationServiceModel;
import com.munjzservice.servicerequest.model.Price;
import com.munjzservice.sharedpreferences.AppSession;
import com.munjzservice.summary.SummaryActivity;
import com.munjzservice.tab.Status;
import com.munjzservice.tab.active.model.RequestAction;
import com.munjzservice.tab.active.model.ServiceRequest;
import com.munjzservice.tab.active.model.StatusChange;
import com.munjzservice.utility.CustomSnackbar;
import com.munjzservice.utility.InternetReachability;
import com.munjzservice.utility.MyProgressDialog;
import com.munjzservice.utility.Utility;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by mac on 1/5/18.
 */

public class ViewServiceRequestVM extends java.util.Observable{

    private Activity activity;
    MyProgressDialog myProgressDialog;
    String serviceRequestId;
    ServiceRequest serviceRequest;

    public final ObservableBoolean isEmpty = new ObservableBoolean(true);

    public ViewServiceRequestVM(@NonNull Activity activity,String serviceRequestId){
        this.activity=activity;
        this.serviceRequestId=serviceRequestId;
        myProgressDialog=new MyProgressDialog();
        isEmpty.set(true);
        notificationServiceCall();
    }

    public void onClickBack(View view){
        activity.finish();
    }

    public void onClickImageView(View view, ServiceRequest serviceRequest){
        Intent intent=new Intent(activity, ImageViewActivity.class);
        intent.putExtra("serviceRequest",serviceRequest);
        activity.startActivity(intent);
    }

    public void onClickLocation(View view, ServiceRequest serviceRequest){
        if (serviceRequest.getId()!=0) {
            Intent intent = new Intent(activity, RequestLocationActivity.class);
            intent.putExtra("serviceRequest", serviceRequest);
            activity.startActivity(intent);
        }
    }

    public void onClickPhoneNumber(View view,ServiceRequest serviceRequest){
        Utility.getInstance().openCallDialPad(activity,serviceRequest.getContactnumber());
    }

    public void onClickCancel(View view, ServiceRequest serviceRequest){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setMessage(activity.getString(R.string.cancel_alert));
        alertDialogBuilder.setPositiveButton(activity.getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        dialog.dismiss();
                        if (serviceRequest.getId()!=0) {
                            callStatusChangeService(serviceRequest, Status.CANCEL);
                        }
                    }
                });

        alertDialogBuilder.setNegativeButton(activity.getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
    public void onClickAccept(View view, ServiceRequest serviceRequest){
        if (serviceRequest.getId()!=0) {
            callStatusChangeService(serviceRequest, Status.ACCEPT);
        }
    }

    public void onClickSummary(View view, ServiceRequest serviceRequest){
        Intent intent=new Intent(activity, SummaryActivity.class);
        intent.putExtra("serviceRequest",serviceRequest);
        activity.startActivity(intent);
    }
    public ServiceRequest getServiceRequest(){
        return serviceRequest;
    }

    private void notificationServiceCall(){
        if (InternetReachability.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService
                    (APICall.class);
            Observable<Response<NotificationServiceModel>> observable = api.notificationServiceRequest(serviceRequestId);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code()==200){
                            if (responses.body().getList().size()!=0) {
                                isEmpty.set(false);
                                serviceRequest = responses.body().getList().get(0);
                            }
                        }else{
                            APIErrorHandler.getInstance().errorHandler(activity,responses.code(),responses.errorBody().string());
                        }
                        setChanged();
                        notifyObservers();
                    }, throwable -> {
                        myProgressDialog.dismissDialog();
                        CustomSnackbar.getInstance().showSnackbar(activity,activity.getString(R.string.something_went_wrong),false);
                        setChanged();
                        notifyObservers();
                    });
        }else {
            CustomSnackbar.getInstance().showSnackbar(activity,activity.getString(R.string.no_internet),false);
            setChanged();
            notifyObservers();
        }
    }

    private void callStatusChangeService(ServiceRequest serviceRequest,Status status){
        if (InternetReachability.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService
                    (APICall.class);
            Observable<Response<StatusChange>> observable;
            if (status==Status.ACCEPT){
                observable = api.changeStatus(serviceRequest.getId(), AppSession.getInstance(activity).getCustomerInfo().getUserid(),"accept");

            }else{
                observable = api.changeStatus(serviceRequest.getId(),AppSession.getInstance(activity).getCustomerInfo().getUserid(),"cancel");
            }

            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code()==200){
                            RequestAction requestAction=new RequestAction();
                            requestAction.setAction(status);
                            requestAction.setServiceRequest(serviceRequest);
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

}

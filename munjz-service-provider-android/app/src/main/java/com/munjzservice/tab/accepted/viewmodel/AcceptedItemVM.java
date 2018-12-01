package com.munjzservice.tab.accepted.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;

import com.munjzservice.location.RequestLocationActivity;
import com.munjzservice.servicerequest.EditServiceRequestActivity;
import com.munjzservice.servicerequest.ImageViewActivity;
import com.munjzservice.summary.SummaryActivity;
import com.munjzservice.tab.active.model.ActiveModel;
import com.munjzservice.tab.active.model.ServiceRequest;
import com.munjzservice.utility.Utility;

/**
 * Created by mac on 12/12/17.
 */

public class AcceptedItemVM {

    public static final int EDIT_REQUEST=100;
    Activity activity;

    Fragment fragment;

    public AcceptedItemVM(@NonNull Activity activity,Fragment fragment){
        this.activity=activity;
        this.fragment=fragment;
    }

    public void onClickEdit(View view, ServiceRequest serviceRequest){
        Intent intent=new Intent(activity, EditServiceRequestActivity.class);
        intent.putExtra("serviceRequest",serviceRequest);
        fragment.startActivityForResult(intent,EDIT_REQUEST);
        //activity.startActivity(intent);
    }

    public void onClickLocation(View view, ServiceRequest serviceRequest){
        Intent intent=new Intent(activity, RequestLocationActivity.class);
        intent.putExtra("serviceRequest",serviceRequest);
        activity.startActivity(intent);
    }

    public void onClickSummary(View view, ServiceRequest serviceRequest){
        Intent intent=new Intent(activity, SummaryActivity.class);
        intent.putExtra("serviceRequest",serviceRequest);
        activity.startActivity(intent);
    }

    public void onClickImageView(View view, ServiceRequest serviceRequest){
        Intent intent=new Intent(activity, ImageViewActivity.class);
        intent.putExtra("serviceRequest",serviceRequest);
        activity.startActivity(intent);
    }

    public void onClickPhoneNumber(View view,ServiceRequest serviceRequest){
        Utility.getInstance().openCallDialPad(activity,serviceRequest.getContactnumber());
    }

    public void onClickRequestDetail(View view, ServiceRequest serviceRequest){
        if (serviceRequest.isShowDetail()){
            serviceRequest.setShowDetail(false);
        }else {
            serviceRequest.setShowDetail(true);
        }
    }
}

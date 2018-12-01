package com.munjzservice.tab.history.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.munjzservice.location.RequestLocationActivity;
import com.munjzservice.servicerequest.ImageViewActivity;
import com.munjzservice.summary.SummaryActivity;
import com.munjzservice.tab.active.model.ServiceRequest;
import com.munjzservice.utility.Utility;

/**
 * Created by mac on 12/16/17.
 */

public class HistoryItemVM {
    Activity activity;
    public HistoryItemVM(@NonNull Activity activity){
        this.activity=activity;
    }

    public void onClickRequestDetail(View view, ServiceRequest serviceRequest){
        if (serviceRequest.isShowDetail()){
            serviceRequest.setShowDetail(false);
        }else {
            serviceRequest.setShowDetail(true);
        }
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

}



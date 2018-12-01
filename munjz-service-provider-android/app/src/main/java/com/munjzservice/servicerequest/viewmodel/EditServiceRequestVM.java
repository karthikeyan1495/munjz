package com.munjzservice.servicerequest.viewmodel;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;


import com.munjzservice.R;
import com.munjzservice.api.APICall;
import com.munjzservice.api.APIConfiguration;
import com.munjzservice.api.APIErrorHandler;
import com.munjzservice.servicerequest.EditServiceRequestActivity;
import com.munjzservice.servicerequest.model.Price;
import com.munjzservice.share.ShareActivity;
import com.munjzservice.sharedpreferences.AppSession;
import com.munjzservice.summary.SummaryActivity;
import com.munjzservice.tab.Status;
import com.munjzservice.tab.active.model.AdditionalService;
import com.munjzservice.tab.active.model.RequestAction;
import com.munjzservice.tab.active.model.ServiceRequest;
import com.munjzservice.tab.active.model.StatusChange;
import com.munjzservice.utility.CustomSnackbar;
import com.munjzservice.utility.InternetReachability;
import com.munjzservice.utility.MyProgressDialog;
import com.munjzservice.utility.StringUtility;
import com.munjzservice.utility.Utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by mac on 12/14/17.
 */

public class EditServiceRequestVM extends java.util.Observable implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    Activity activity;

    int year, month, date;
    int hour, miniute, seconds;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    Price price;
    ServiceRequest serviceRequest;

    MyProgressDialog myProgressDialog;

    public boolean isUpdated=false;

    String startDate;

    boolean isStartDate=false;
    boolean isStartTime=false;

    public EditServiceRequestVM(@NonNull Activity activity, ServiceRequest serviceRequest,Price price){
        this.activity=activity;
        this.serviceRequest=serviceRequest;
        this.price=price;
        myProgressDialog=new MyProgressDialog();
        startDate=serviceRequest.getDate();
    }

    public void onClickBack(View view){
        Intent intent=new Intent();
        intent.putExtra("isUpdated",isUpdated);
        activity.setResult(Activity.RESULT_OK,intent);
        activity.finish();
    }

    public void onClickPhoneNumber(View view,ServiceRequest serviceRequest){
        Utility.getInstance().openCallDialPad(activity,serviceRequest.getContactnumber());
    }

    public void onClickAdditionalService(View view){
        if (activity instanceof EditServiceRequestActivity){
            EditServiceRequestActivity editServiceRequestActivity=(EditServiceRequestActivity)activity;
            if (editServiceRequestActivity.adapter!=null){
                editServiceRequestActivity.adapter.addAdditionalService();
            }
        }
    }

    public void OnClickStatusPicker(View view){

        final CharSequence[] items = activity.getResources().getStringArray(R.array.status_array);
        final CharSequence[] items_key = activity.getResources().getStringArray(R.array.status_array_key);


        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int position) {
                //mDoneButton.setText(items[item]);
                serviceRequest.setStatus(String.valueOf(items[position]));
                serviceRequest.setStatus_key(String.valueOf(items_key[position]));
                setChanged();
                notifyObservers(serviceRequest);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }
    public void onClickSummary(View view, ServiceRequest serviceRequest){
        Intent intent=new Intent(activity, SummaryActivity.class);
        intent.putExtra("serviceRequest",serviceRequest);
        activity.startActivity(intent);
    }
    public void onClickSave(View view ,ServiceRequest serviceRequest){
        saveChanges(serviceRequest,false);

    }

    public void onClickShare(View view ,ServiceRequest serviceRequest){
        saveChanges(serviceRequest,true);

    }


    private void saveChanges(ServiceRequest serviceRequest,boolean isShare){
        boolean isValidated=true;

        for (AdditionalService additionalService:serviceRequest.getAdditional_service()){
            if (additionalService.getService_name().trim().equals("")||additionalService.getPrice().trim().equals("")){
                isValidated=false;
                break;
            }
        }

        if (isValidated) {
            if (!price.getPrice().trim().equals("")) {
                try {
                    float finalPrice = Float.valueOf(price.getPrice().trim());
                    if (serviceRequest.getIsdataservice()==1){
                        if (finalPrice < 50) {
                            serviceRequest.setTotal_price("50");
                        } else {
                            serviceRequest.setTotal_price(price.getPrice());
                        }
                    }else {
                        if (serviceRequest.getAdditional_service().size()!=0) {
                            if (finalPrice < 50) {
                                serviceRequest.setTotal_price("50");
                            } else {
                                serviceRequest.setTotal_price(price.getPrice());
                            }
                        }else{
                            serviceRequest.setTotal_price(price.getPrice());
                        }
                    }
                    updateServiceRequestChanges(serviceRequest,isShare);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else{
            CustomSnackbar.getInstance().showSnackbar(activity,activity.getString(R.string.additional_service_empty),false);
        }
    }

    public void onClickDataPicker(View view){
        isStartDate=true;
        Calendar calendar = Calendar.getInstance();
        String dateArray[]=serviceRequest.getDate().split("-");
        year=Integer.valueOf(dateArray[0]);
        month=Integer.valueOf(dateArray[1])-1;
        date=Integer.valueOf(dateArray[2]);
        //date = calendar.get(Calendar.DATE);
        //year = calendar.get(Calendar.YEAR);
        //month = calendar.get(Calendar.MONTH);
        datePickerDialog = new DatePickerDialog(activity, R.style.DialogTheme, this, year, month, date);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    public void onClickTimePicker(View view){
        isStartTime=true;
        Calendar calendar = Calendar.getInstance();
        String timeArray[]=serviceRequest.getStarttime().split(":");
        hour=Integer.valueOf(timeArray[0]);
        miniute=Integer.valueOf(timeArray[1]);
        //hour = calendar.get(Calendar.HOUR);
        //miniute = calendar.get(Calendar.MINUTE);
        timePickerDialog = new TimePickerDialog(activity, R.style.DialogTheme,this, hour, miniute, DateFormat.is24HourFormat(activity));
        timePickerDialog.show();
    }

    public void onClickEndDatePicker(View view){
        isStartDate=false;
        Calendar calendar = Calendar.getInstance();
        String dateArray[]=StringUtility.getDate(serviceRequest.getTeam_appointment_end_time()).split("-");
        year=Integer.valueOf(dateArray[0]);
        month=Integer.valueOf(dateArray[1])-1;
        date=Integer.valueOf(dateArray[2]);
        //date = calendar.get(Calendar.DATE);
        //year = calendar.get(Calendar.YEAR);
        //month = calendar.get(Calendar.MONTH);
        datePickerDialog = new DatePickerDialog(activity, R.style.DialogTheme, this, year, month, date);
        datePickerDialog.getDatePicker().setMinDate(Utility.getInstance().getTimeStamp(StringUtility.getDate(serviceRequest.getTeam_appointment_start_time())));
        datePickerDialog.show();

    }

    public void onClickEndTimePicker(View view){
        isStartTime=false;
        Calendar calendar = Calendar.getInstance();
        String timeArray[]=StringUtility.getTime(serviceRequest.getTeam_appointment_end_time()).split(":");
        hour=Integer.valueOf(timeArray[0]);
        miniute=Integer.valueOf(timeArray[1]);
        //hour = calendar.get(Calendar.HOUR);
        //miniute = calendar.get(Calendar.MINUTE);
        timePickerDialog = new TimePickerDialog(activity, R.style.DialogTheme,this, hour, miniute, DateFormat.is24HourFormat(activity));
        timePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        int months = month + 1;
        this.year=year;
        this.month=months;
        this.date=dayOfMonth;
        String monthString=String.valueOf(this.month);
        String dateString=String.valueOf(this.date);
        if (this.month<=9){
            monthString="0"+String.valueOf(this.month);
        }
        if (this.date<=9){
            dateString="0"+String.valueOf(this.date);
        }

        if (isStartDate) {
            serviceRequest.setDate(String.valueOf(year + "-" + monthString + "-" + dateString));
            serviceRequest.setTeam_appointment_start_time(serviceRequest.getDate() + " " + serviceRequest.getStarttime());
        }else{
            String timeString=StringUtility.getTime(serviceRequest.getTeam_appointment_end_time());
            serviceRequest.setTeam_appointment_end_time(String.valueOf(year + "-" + monthString + "-" + dateString)+" "+timeString);
        }
        setChanged();
        notifyObservers(serviceRequest);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String hourOfDayString=String.valueOf(hourOfDay);
        String minuteString=String.valueOf(minute);

        if (hourOfDay<=9){
            hourOfDayString="0"+String.valueOf(hourOfDay);
        }

        if(minute<=9){
            minuteString="0"+String.valueOf(minute);
        }

        if (isStartTime) {
            serviceRequest.setStarttime(String.valueOf(hourOfDayString + ":" + minuteString));
            serviceRequest.setTeam_appointment_start_time(serviceRequest.getDate() + " " + serviceRequest.getStarttime());
        }else{
            String dateString=StringUtility.getDate(serviceRequest.getTeam_appointment_end_time());
            serviceRequest.setTeam_appointment_end_time(dateString+" "+String.valueOf(hourOfDayString + ":" + minuteString));
        }
        setChanged();
        notifyObservers(serviceRequest);
    }

    private void updateServiceRequestChanges(ServiceRequest serviceRequest,boolean isShare){
        if (InternetReachability.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService
                    (APICall.class);
            Observable<Response<StatusChange>> observable = api.updateServiceRequest(serviceRequest.getId(),serviceRequest);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code()==200){
                            isUpdated=true;
                            serviceRequest.setTotal_price(activity.getString(R.string.sar)+" "+serviceRequest.getTotal_price());
                            setChanged();
                            notifyObservers(serviceRequest);
                            APIErrorHandler.getInstance().errorHandler(activity,responses.code(),responses.body().getStatus());
                            if(isShare){
                                Intent intent=new Intent(activity, ShareActivity.class);
                                intent.putExtra("serviceRequest",serviceRequest);
                                activity.startActivity(intent);
                            }
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

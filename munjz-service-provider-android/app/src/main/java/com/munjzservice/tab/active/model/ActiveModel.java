package com.munjzservice.tab.active.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.munjzservice.BR;

import java.io.Serializable;
import java.util.List;


/**
 * Created by mac on 12/12/17.
 */

public class ActiveModel implements Serializable{



    @SerializedName("statusCode")
    @Expose
    int statusCode;

    @SerializedName("acceptcount")
    @Expose
    int acceptcount;

    @SerializedName("requestcount")
    @Expose
    int requestcount;

    @SerializedName("historycount")
    @Expose
    int historycount;

    @SerializedName("list")
    @Expose
    List<ServiceRequest> list;



    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getAcceptcount() {
        return acceptcount;
    }

    public void setAcceptcount(int acceptcount) {
        this.acceptcount = acceptcount;
    }

    public int getRequestcount() {
        return requestcount;
    }

    public void setRequestcount(int requestcount) {
        this.requestcount = requestcount;
    }

    public int getHistorycount() {
        return historycount;
    }

    public void setHistorycount(int historycount) {
        this.historycount = historycount;
    }

    public List<ServiceRequest> getList() {
        return list;
    }

    public void setList(List<ServiceRequest> list) {
        this.list = list;
    }
}

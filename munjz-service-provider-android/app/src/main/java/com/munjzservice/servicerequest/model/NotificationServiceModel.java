package com.munjzservice.servicerequest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.munjzservice.tab.active.model.ServiceRequest;

import java.util.List;

/**
 * Created by mac on 1/6/18.
 */

public class NotificationServiceModel {

    @SerializedName("statusCode")
    @Expose
    int statusCode;

    @SerializedName("list")
    @Expose
    List<ServiceRequest> list;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<ServiceRequest> getList() {
        return list;
    }

    public void setList(List<ServiceRequest> list) {
        this.list = list;
    }
}

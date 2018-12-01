package com.munjzservice.tab.active.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mac on 12/16/17.
 */

public class StatusChange implements Serializable{

    @SerializedName("statusCode")
    @Expose
    int statusCode;

    @SerializedName("status")
    @Expose
    String status;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

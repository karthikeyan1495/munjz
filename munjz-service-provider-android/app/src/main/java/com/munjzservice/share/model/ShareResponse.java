package com.munjzservice.share.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mac on 2/6/18.
 */

public class ShareResponse {

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

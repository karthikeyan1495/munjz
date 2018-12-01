package com.munjzservice.balance.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mac on 2/5/18.
 */

public class BalanceResponse {

    @SerializedName("statusCode")
    @Expose
    int statusCode;
    @SerializedName("balance_details")
    @Expose
    Balance balance_details;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Balance getBalance_details() {
        return balance_details;
    }

    public void setBalance_details(Balance balance_details) {
        this.balance_details = balance_details;
    }
}

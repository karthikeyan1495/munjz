package com.munjzservice.balance.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mac on 2/5/18.
 */

public class Balance {

    @SerializedName("advance_amount")
    @Expose
    String advance_amount="";
    @SerializedName("balance_amount")
    @Expose
    String balance_amount="";
    @SerializedName("complete_count")
    @Expose
    String complete_count="";

    public String getAdvance_amount() {
        return advance_amount;
    }

    public void setAdvance_amount(String advance_amount) {
        this.advance_amount = advance_amount;
    }

    public String getBalance_amount() {
        return balance_amount;
    }

    public void setBalance_amount(String balance_amount) {
        this.balance_amount = balance_amount;
    }

    public String getComplete_count() {
        return complete_count;
    }

    public void setComplete_count(String complete_count) {
        this.complete_count = complete_count;
    }
}

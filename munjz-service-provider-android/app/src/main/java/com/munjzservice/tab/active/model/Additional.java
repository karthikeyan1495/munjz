package com.munjzservice.tab.active.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mac on 12/16/17.
 */

public class Additional implements Serializable {

    @SerializedName("servicename")
    @Expose
    String servicename;

    @SerializedName("serviceprice")
    @Expose
    String serviceprice;

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public String getServiceprice() {
        return serviceprice;
    }

    public void setServiceprice(String serviceprice) {
        this.serviceprice = serviceprice;
    }
}

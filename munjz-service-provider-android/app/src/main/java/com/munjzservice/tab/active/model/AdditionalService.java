package com.munjzservice.tab.active.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mac on 2/4/18.
 */

public class AdditionalService implements Serializable {

    @SerializedName("service_name")
    @Expose
    String service_name="";

    @SerializedName("price")
    @Expose
    String price="";

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

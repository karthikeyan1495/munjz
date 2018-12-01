package com.munjzservice.summary.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mac on 2/25/18.
 */

public class Values {

    @SerializedName("label")
    @Expose
    String label;

    @SerializedName("count")
    @Expose
    String count;

    @SerializedName("value")
    @Expose
    String value;

    @SerializedName("price")
    @Expose
    String price;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

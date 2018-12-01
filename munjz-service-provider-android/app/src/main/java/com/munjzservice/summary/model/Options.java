package com.munjzservice.summary.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mac on 2/25/18.
 */

public class Options {

    @SerializedName("lable_name")
    @Expose
    String lable_name;

    @SerializedName("values")
    @Expose
    List<Values> values;

    public String getLable_name() {
        return lable_name;
    }

    public void setLable_name(String lable_name) {
        this.lable_name = lable_name;
    }

    public List<Values> getValues() {
        return values;
    }

    public void setValues(List<Values> values) {
        this.values = values;
    }
}

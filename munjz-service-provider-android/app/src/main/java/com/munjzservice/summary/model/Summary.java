package com.munjzservice.summary.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mac on 2/25/18.
 */

public class Summary {

    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("title")
    @Expose
    String title;

    @SerializedName("request_no")
    @Expose
    String request_no;

    @SerializedName("options")
    @Expose
    List<Options> options;

    @SerializedName("additional")
    @Expose
    Object additional;

    @SerializedName("grand_total")
    @Expose
    String grand_total;

    @SerializedName("statusCode")
    @Expose
    int statusCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRequest_no() {
        return request_no;
    }

    public void setRequest_no(String request_no) {
        this.request_no = request_no;
    }

    public List<Options> getOptions() {
        return options;
    }

    public void setOptions(List<Options> options) {
        this.options = options;
    }

    public Object getAdditional() {
        return additional;
    }

    public void setAdditional(Object additional) {
        this.additional = additional;
    }

    public String getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(String grand_total) {
        this.grand_total = grand_total;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}

package com.munjzservice.share.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mac on 2/6/18.
 */

public class ShareRequest {
    @SerializedName("title")
    @Expose
    String title;
    @SerializedName("request_no")
    @Expose
    String request_no;
    @SerializedName("comments")
    @Expose
    String comments;
    @SerializedName("email_ids")
    @Expose
    List<String> email_ids;

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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<String> getEmail_ids() {
        return email_ids;
    }

    public void setEmail_ids(List<String> email_ids) {
        this.email_ids = email_ids;
    }
}

package com.munjzservice.profile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mac on 12/14/17.
 */

public class Customer implements Serializable{

    @SerializedName("userid")
    @Expose
    int userid;

    @SerializedName("username")
    @Expose
    String username;

    @SerializedName("email")
    @Expose
    String email;

    @SerializedName("contactnumber")
    @Expose
    String contactnumber;

    @SerializedName("region")
    @Expose
    String region;

    @SerializedName("services")
    @Expose
    String services;

    @SerializedName("statusCode")
    @Expose
    int statusCode;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactnumber() {
        return contactnumber;
    }

    public void setContactnumber(String contactnumber) {
        this.contactnumber = contactnumber;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

package com.munjzservice.login.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mac on 12/14/17.
 */

public class LoginRequestModel {
    @SerializedName("email")
    @Expose
    String email;
    @SerializedName("password")
    @Expose
    String password;
    @SerializedName("device_token")
    @Expose
    String device_token;
    @SerializedName("device_type")
    @Expose
    String device_type;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }
}


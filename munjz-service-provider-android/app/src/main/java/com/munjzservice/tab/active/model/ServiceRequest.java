package com.munjzservice.tab.active.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.munjzservice.BR;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mac on 12/16/17.
 */

public class ServiceRequest extends BaseObservable implements Serializable {

    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("serviceid")
    @Expose
    String serviceid;

    @SerializedName("title")
    @Expose
    String title;

    @SerializedName("contact_name")
    @Expose
    String contact_name;

    @SerializedName("team_appointment_start_time")
    @Expose
    String team_appointment_start_time;

    @SerializedName("team_appointment_end_time")
    @Expose
    String team_appointment_end_time;

    @SerializedName("region")
    @Expose
    String region;

    @SerializedName("techlead")
    @Expose
    String techlead;

    @SerializedName("appointment_mobile")
    @Expose
    String appointment_mobile;

    @SerializedName("teams_appointment_location")
    @Expose
    String teams_appointment_location;

    @SerializedName("teams_appointment_city")
    @Expose
    String teams_appointment_city;

    @SerializedName("teams_appointment_country")
    @Expose
    String teams_appointment_country;

    @SerializedName("contactnumber")
    @Expose
    String contactnumber;

    @SerializedName("date")
    @Expose
    String date;

    @SerializedName("starttime")
    @Expose
    String starttime;

    @SerializedName("total_price")
    @Expose
    String total_price;

    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("additionalservices")
    @Expose
    List<Additional> additional;

    @SerializedName("latitude")
    @Expose
    String latitude;

    @SerializedName("longitude")
    @Expose
    String longitude;

    @SerializedName("status_key")
    @Expose
    String status_key;

    @SerializedName("additional_service")
    @Expose
    List<AdditionalService> additional_service;

    @SerializedName("isdataservice")
    @Expose
    int isdataservice = 1;

    @SerializedName("comments")
    @Expose
    String comments;

    @SerializedName("images")
    @Expose
    List<String> images;

    @SerializedName("othercost")
    @Expose
    String othercost;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServiceid() {
        return serviceid;
    }

    public void setServiceid(String serviceid) {
        this.serviceid = serviceid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getTeam_appointment_start_time() {
        return team_appointment_start_time;
    }

    public void setTeam_appointment_start_time(String team_appointment_start_time) {
        this.team_appointment_start_time = team_appointment_start_time;
    }

    public String getTeam_appointment_end_time() {
        return team_appointment_end_time;
    }

    public void setTeam_appointment_end_time(String team_appointment_end_time) {
        this.team_appointment_end_time = team_appointment_end_time;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getTechlead() {
        return techlead;
    }

    public void setTechlead(String techlead) {
        this.techlead = techlead;
    }

    public String getAppointment_mobile() {
        return appointment_mobile;
    }

    public void setAppointment_mobile(String appointment_mobile) {
        this.appointment_mobile = appointment_mobile;
    }

    public String getTeams_appointment_location() {
        return teams_appointment_location;
    }

    public void setTeams_appointment_location(String teams_appointment_location) {
        this.teams_appointment_location = teams_appointment_location;
    }

    public String getTeams_appointment_city() {
        return teams_appointment_city;
    }

    public void setTeams_appointment_city(String teams_appointment_city) {
        this.teams_appointment_city = teams_appointment_city;
    }

    public String getTeams_appointment_country() {
        return teams_appointment_country;
    }

    public void setTeams_appointment_country(String teams_appointment_country) {
        this.teams_appointment_country = teams_appointment_country;
    }

    public String getContactnumber() {
        return contactnumber;
    }

    public void setContactnumber(String contactnumber) {
        this.contactnumber = contactnumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Additional> getAdditional() {
        return additional;
    }

    public void setAdditional(List<Additional> additionalServices) {
        this.additional = additionalServices;
    }


    boolean showDetail = false;

    @Bindable
    public boolean isShowDetail() {
        return showDetail;
    }

    public void setShowDetail(boolean showDetail) {
        this.showDetail = showDetail;
        notifyPropertyChanged(BR.showDetail);
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getStatus_key() {
        return status_key;
    }

    public void setStatus_key(String status_key) {
        this.status_key = status_key;
    }

    public List<AdditionalService> getAdditional_service() {
        return additional_service;
    }

    public void setAdditional_service(List<AdditionalService> additional_service) {
        this.additional_service = additional_service;
    }

    public int getIsdataservice() {
        return isdataservice;
    }

    public void setIsdataservice(int isdataservice) {
        this.isdataservice = isdataservice;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getOthercost() {
        return othercost;
    }

    public void setOthercost(String othercost) {
        this.othercost = othercost;
    }
}

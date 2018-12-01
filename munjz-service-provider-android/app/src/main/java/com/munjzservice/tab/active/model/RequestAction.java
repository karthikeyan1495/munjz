package com.munjzservice.tab.active.model;

import com.munjzservice.tab.Status;

/**
 * Created by mac on 12/16/17.
 */

public class RequestAction {

    Status action;
    ServiceRequest serviceRequest;

    public Status getAction() {
        return action;
    }

    public void setAction(Status action) {
        this.action = action;
    }

    public ServiceRequest getServiceRequest() {
        return serviceRequest;
    }

    public void setServiceRequest(ServiceRequest serviceRequest) {
        this.serviceRequest = serviceRequest;
    }
}

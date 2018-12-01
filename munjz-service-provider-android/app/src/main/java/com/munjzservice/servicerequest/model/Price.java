package com.munjzservice.servicerequest.model;

import com.munjzservice.utility.StringUtility;

/**
 * Created by mac on 12/16/17.
 */

public class Price {

    String price;

    public Price(String price){
        this.price=price;
    }

    public String getPrice() {
        return price.trim();
    }

    public void setPrice(String price) {
        this.price = price.trim();
    }

}

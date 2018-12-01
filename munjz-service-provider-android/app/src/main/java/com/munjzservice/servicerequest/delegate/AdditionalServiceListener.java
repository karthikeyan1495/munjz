package com.munjzservice.servicerequest.delegate;

import android.view.View;

import com.munjzservice.tab.active.model.AdditionalService;

/**
 * Created by mac on 2/5/18.
 */

public interface AdditionalServiceListener {

    public void onAdditionalServiceDeleted(View view,AdditionalService additionalService);
    public void onAdditionalServicePriceChanged(float newValue,AdditionalService additionalService);

}

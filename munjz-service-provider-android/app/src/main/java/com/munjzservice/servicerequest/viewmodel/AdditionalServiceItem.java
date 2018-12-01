package com.munjzservice.servicerequest.viewmodel;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.munjzservice.servicerequest.delegate.AdditionalServiceListener;
import com.munjzservice.tab.active.model.AdditionalService;

/**
 * Created by mac on 2/4/18.
 */

public class AdditionalServiceItem {

    Activity activity;

    AdditionalServiceListener deleteListener;

    public AdditionalServiceItem(@NonNull Activity activity,AdditionalServiceListener deleteListener){
        this.activity=activity;
        this.deleteListener=deleteListener;
    }

    public void onClickDelete(View view, AdditionalService additionalService){
        if (deleteListener!=null){
            deleteListener.onAdditionalServiceDeleted(view,additionalService);
        }
    }
    public void onTextChanged(CharSequence s, int start, int before, int count, AdditionalService additionalService) {
        Log.w("tag", "onTextChanged " + additionalService.getPrice()+"  "+s);
        if (deleteListener!=null){
            try {
                float newValue=Float.valueOf(s.toString());
                deleteListener.onAdditionalServicePriceChanged(newValue,additionalService);
            }catch (Exception e){
                deleteListener.onAdditionalServicePriceChanged(0,additionalService);
            }
        }
    }
}

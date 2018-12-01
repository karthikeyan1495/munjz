package com.munjzservice.location;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by mac on 12/26/17.
 */

public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {

    Activity activity;
    public CustomInfoWindow(Activity activity) {
        this.activity=activity;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return(null);
    }

    @SuppressLint("InflateParams")
    @Override
    public View getInfoContents(Marker marker) {
        LinearLayout info = new LinearLayout(activity);
        info.setOrientation(LinearLayout.VERTICAL);
        TextView snippet = new TextView(activity);
        snippet.setTextColor(Color.GRAY);
        snippet.setText(marker.getSnippet());
        info.addView(snippet);
        return info;
    }
}
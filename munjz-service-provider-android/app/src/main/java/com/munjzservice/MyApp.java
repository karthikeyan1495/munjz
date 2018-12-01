package com.munjzservice;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.munjzservice.font.CustomFontFamily;
import com.munjzservice.sharedpreferences.AppSession;
import com.munjzservice.utility.InternetReachability;

import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * Created by mac on 12/11/17.
 */

public class MyApp extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        this.context = this;
        InternetReachability.getInstance().init();
        CustomFontFamily.getInstance().addAllFont(this);
        MultiDex.install(this);
        AppSession.getInstance(this).saveBadgeCount(0);
        ShortcutBadger.removeCount(context);
    }
    public static Context getContext() {
        return context;
    }

}
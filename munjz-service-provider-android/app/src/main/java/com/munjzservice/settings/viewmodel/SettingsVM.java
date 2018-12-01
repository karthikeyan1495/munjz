package com.munjzservice.settings.viewmodel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import com.munjzservice.R;
import com.munjzservice.settings.SettingsActivity;
import com.munjzservice.sharedpreferences.AppSession;
import com.munjzservice.tab.MainActivity;
import com.munjzservice.utility.Utility;

/**
 * Created by mac on 1/19/18.
 */

public class SettingsVM {

    Activity activity;

    public ObservableField<String> language=new ObservableField<>("");

    public boolean isLanguageChanged=false;

    public SettingsVM(@NonNull Activity activity,boolean isLanguageChanged) {
        this.activity = activity;
        this.isLanguageChanged=isLanguageChanged;
        setLanguageName();
    }

    private void setLanguageName(){
        if (AppSession.getInstance(activity).getLanguageKey().equals(activity.getString(R.string.ar))){
            language.set(activity.getString(R.string.arabic));
        }else{
            language.set(activity.getString(R.string.english));
        }
    }


    public void onClickBack(View view) {
        if (isLanguageChanged){
            activity.finishAffinity();
            activity.startActivity(new Intent(activity, MainActivity.class));

        }else{
            activity.finish();
        }
    }

    public void onClickLanguage(View view) {
        languagePicker();
    }

    private void languagePicker() {
        final CharSequence[] languageName = activity.getResources().getStringArray(R.array.language_array);
        final CharSequence[] languageKey = activity.getResources().getStringArray(R.array.language_key_array);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setItems(languageName, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                dialog.dismiss();
                if (!language.get().equals(String.valueOf(languageName[item]))) {
                    AppSession.getInstance(activity).saveLanguageKey(String.valueOf(languageKey[item]));
                    //isLanguageChanged = true;
                    activity.onConfigurationChanged(Utility.getInstance().setLanguage(activity));
                   // activity.finishAffinity();
                    //activity.startActivity(new Intent(activity, SettingsActivity.class));
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}

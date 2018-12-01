package com.munjzservice.settings;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.munjzservice.R;
import com.munjzservice.databinding.ActivitySettingsBinding;
import com.munjzservice.settings.viewmodel.SettingsVM;
import com.munjzservice.tab.MainActivity;

public class SettingsActivity extends AppCompatActivity {

    ActivitySettingsBinding binding;
    SettingsVM settingsVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView(false);
    }

    private void bindView(boolean isLanguageChanged){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_settings);
        settingsVM=new SettingsVM(this,isLanguageChanged);
        binding.setSettingsVM(settingsVM);
        setSupportActionBar(binding.toolbar);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getBaseContext().getResources().updateConfiguration(newConfig, getBaseContext().getResources().getDisplayMetrics());
        bindView(true);
    }

    @Override
    public void onBackPressed() {
        if (settingsVM.isLanguageChanged) {
            finishAffinity();
            startActivity(new Intent(this, MainActivity.class));
        }else {
            finish();
        }
        super.onBackPressed();

    }
}

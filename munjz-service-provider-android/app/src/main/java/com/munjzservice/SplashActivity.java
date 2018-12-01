package com.munjzservice;

import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.munjzservice.databinding.ActivitySplashBinding;
import com.munjzservice.login.LoginActivity;
import com.munjzservice.sharedpreferences.AppSession;
import com.munjzservice.tab.MainActivity;
import com.munjzservice.utility.Utility;

import java.util.Locale;

import me.leolin.shortcutbadger.ShortcutBadger;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utility.getInstance().setLanguage(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        initLoading();

    }

    private void initLoading() {
        new Thread(new Runnable() {
            public void run() {
                initProgress();
            }
        }).start();
    }

    private void initProgress() {
        for (int progress = 1; progress <= 100; progress += 1) {
            try {
                binding.progressBar.setProgress(progress);
                Thread.sleep(20);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        moveToNextScreen();
    }

    private void moveToNextScreen() {
        if (AppSession.getInstance(this).isLogin()) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        } else {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        }
        finish();
    }


}

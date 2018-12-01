package com.munjzservice.login;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.munjzservice.R;
import com.munjzservice.databinding.ActivityLoginBinding;
import com.munjzservice.login.model.UserInfo;
import com.munjzservice.login.viewmodel.LoginVM;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    LoginVM loginVM;
    UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
    }

    private void initBinding(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_login);
        userInfo=new UserInfo();
        loginVM=new LoginVM(this,userInfo);
        binding.setLoginVM(loginVM);
        binding.setUserInfo(userInfo);
    }
}

package com.munjzservice.profile;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.munjzservice.R;
import com.munjzservice.databinding.ActivityProfileBinding;
import com.munjzservice.profile.model.Customer;
import com.munjzservice.profile.viewmodel.ProfileVM;
import com.munjzservice.sharedpreferences.AppSession;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;
    ProfileVM profileVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
    }

    private void initBinding(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_profile);
        profileVM=new ProfileVM(this);
        binding.setProfileVM(profileVM);
        Customer customer= AppSession.getInstance(this).getCustomerInfo();
        binding.setCustomer(customer);
        setSupportActionBar(binding.toolbar);
    }
}

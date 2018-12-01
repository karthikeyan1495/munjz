package com.munjzservice.servicerequest;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.munjzservice.R;
import com.munjzservice.databinding.ActivityImageViewBinding;
import com.munjzservice.servicerequest.adapter.CustomPagerAdapter;
import com.munjzservice.servicerequest.viewmodel.ImageViewVM;
import com.munjzservice.tab.active.model.ServiceRequest;


public class ImageViewActivity extends AppCompatActivity {

    ActivityImageViewBinding binding;
    ImageViewVM imageViewVM;
    ServiceRequest serviceRequest;
    CustomPagerAdapter customPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         getIntentData();
         bindView();
         setupImageView();
    }
    private void getIntentData(){
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            serviceRequest=(ServiceRequest) bundle.getSerializable("serviceRequest");
        }
    }
    private void bindView(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_image_view);
        imageViewVM=new ImageViewVM(this);
        binding.setImageViewVM(imageViewVM);
        binding.setServiceRequest(serviceRequest);
        setSupportActionBar(binding.toolbar);
    }
    private void setupImageView(){
        customPagerAdapter=new CustomPagerAdapter(this,serviceRequest.getImages());
        binding.pager.setAdapter(customPagerAdapter);
    }

}

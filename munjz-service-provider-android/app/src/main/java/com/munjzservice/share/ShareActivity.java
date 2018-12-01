package com.munjzservice.share;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.munjzservice.R;
import com.munjzservice.databinding.ActivityShareBinding;
import com.munjzservice.servicerequest.adapter.AdditionalServiceAdapter;
import com.munjzservice.share.adapter.ShareAdditionalServiceAdapter;
import com.munjzservice.share.model.EmailData;
import com.munjzservice.share.viewmodel.ShareVM;
import com.munjzservice.tab.active.model.ServiceRequest;

public class ShareActivity extends AppCompatActivity {

    ServiceRequest serviceRequest=new ServiceRequest();
    ActivityShareBinding binding;
    ShareVM shareVM;
    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
    ShareAdditionalServiceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentData();
        bindView();
        setupRecyclerView();
    }
    private void getIntentData(){
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            serviceRequest=(ServiceRequest) bundle.getSerializable("serviceRequest");
        }
    }
    private void bindView(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_share);
        shareVM=new ShareVM(this,serviceRequest);
        binding.setShareVM(shareVM);
        binding.setServiceRequest(serviceRequest);
        binding.setEmailData(new EmailData());
        setSupportActionBar(binding.toolbar);
    }

    private void setupRecyclerView(){
        adapter=new ShareAdditionalServiceAdapter(this,serviceRequest.getAdditional_service());
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setAdapter(adapter);
    }
}

package com.munjzservice.servicerequest;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ArrayAdapter;

import com.munjzservice.R;
import com.munjzservice.databinding.ActivityEditServiceRequestBinding;
import com.munjzservice.servicerequest.adapter.AdditionalServiceAdapter;
import com.munjzservice.servicerequest.model.Price;
import com.munjzservice.servicerequest.viewmodel.EditServiceRequestVM;
import com.munjzservice.tab.accepted.adapter.AcceptedItemAdapter;
import com.munjzservice.tab.accepted.viewmodel.AcceptedListVM;
import com.munjzservice.tab.active.model.AdditionalService;
import com.munjzservice.tab.active.model.ServiceRequest;
import com.munjzservice.utility.StringUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class EditServiceRequestActivity extends AppCompatActivity implements Observer {

    ActivityEditServiceRequestBinding binding;
    EditServiceRequestVM editServiceRequestVM;
    ServiceRequest serviceRequest=new ServiceRequest();
    Price price;

    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
    public AdditionalServiceAdapter adapter;

    float additionalServiceCost=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentData();
        getAdditionalServiceCost();
        initBinding();
        setupRecyclerView();
        setUpObserver(editServiceRequestVM);
    }

    private void setupRecyclerView(){
        adapter=new AdditionalServiceAdapter(this,serviceRequest.getAdditional_service());
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setAdapter(adapter);
    }


    public void setUpObserver(Observable observable) {
        observable.addObserver(this);
    }

    private void initBinding(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_edit_service_request);
        price=new Price(StringUtility.removeString(serviceRequest.getTotal_price()));
        editServiceRequestVM=new EditServiceRequestVM(this,serviceRequest,price);
        binding.setEditServiceRequestVM(editServiceRequestVM);
        binding.setPrice(price);
        binding.setServiceRequest(serviceRequest);
        setSupportActionBar(binding.toolbar);
    }

    private void getIntentData(){
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            serviceRequest=(ServiceRequest) bundle.getSerializable("serviceRequest");
        }
    }

    private void getAdditionalServiceCost(){
        if (serviceRequest!=null){
            for (AdditionalService additionalService:serviceRequest.getAdditional_service()){
                if (additionalService.getPrice().trim().length()!=0) {
                    additionalServiceCost=additionalServiceCost+Float.valueOf(additionalService.getPrice().trim());
                }
            }
        }

    }

    public void minusPrice(float value){
            if (!price.getPrice().trim().equals("")) {
                try {
                    float originalValue = Float.valueOf(price.getPrice().trim());
                    float finalValue = originalValue - value;
                    if (serviceRequest.getIsdataservice()==1) {
                        if (Integer.valueOf(serviceRequest.getOthercost())!=0) {
                            additionalServiceCost = additionalServiceCost - value;
                            float total_due = Float.valueOf(serviceRequest.getOthercost()) + additionalServiceCost;
                            price.setPrice(String.valueOf(total_due));
                        }else{
                            additionalServiceCost = additionalServiceCost - value;
                            price.setPrice(String.valueOf(additionalServiceCost));
                        }
                    }else{
                        if (serviceRequest.getAdditional_service().size()==0){
                            additionalServiceCost=0;
                            price.setPrice("0");
                        }else{
                            additionalServiceCost = additionalServiceCost - value;
                            price.setPrice(String.valueOf(additionalServiceCost));
                        }
                    }
                    binding.setPrice(price);
                } catch (Exception e) {
                }
            }
    }

    public void updatePrice(float oldValue,float newValue){
            if (!price.getPrice().trim().equals("")) {
                try {
                    if (serviceRequest.getIsdataservice()==0) {
                        //float originalValue = Float.valueOf(price.getPrice().trim());
                        //float finalValue = originalValue - oldValue;
                        //finalValue = finalValue + newValue;
                        additionalServiceCost=additionalServiceCost-oldValue;
                        additionalServiceCost=additionalServiceCost+newValue;
                        price.setPrice(String.valueOf(additionalServiceCost));
                        binding.setPrice(price);
                    }else{

                        if (Integer.valueOf(serviceRequest.getOthercost())!=0){
                            additionalServiceCost=additionalServiceCost-oldValue;
                            additionalServiceCost=additionalServiceCost+newValue;
                            float total_due=Float.valueOf(serviceRequest.getOthercost())+additionalServiceCost;
                            price.setPrice(String.valueOf(total_due));
                            binding.setPrice(price);
                        }else{
                            additionalServiceCost=additionalServiceCost-oldValue;
                            additionalServiceCost=additionalServiceCost+newValue;
                            price.setPrice(String.valueOf(additionalServiceCost));
                            binding.setPrice(price);
                        }

                    }
                } catch (Exception e) {
                }
            }
    }

    @Override
    public void update(Observable observable, Object object) {
        if(observable instanceof EditServiceRequestVM) {
            ServiceRequest serviceRequest=(ServiceRequest) object;
            binding.setServiceRequest(serviceRequest);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent();
        intent.putExtra("isUpdated",editServiceRequestVM.isUpdated);
        setResult(Activity.RESULT_OK,intent);
        finish();
        super.onBackPressed();
    }
}

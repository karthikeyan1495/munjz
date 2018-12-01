package com.munjzservice.summary;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.munjzservice.R;
import com.munjzservice.databinding.ActivitySummaryBinding;
import com.munjzservice.servicerequest.adapter.AdditionalServiceAdapter;
import com.munjzservice.servicerequest.viewmodel.EditServiceRequestVM;
import com.munjzservice.summary.adapter.SummaryAdapter;
import com.munjzservice.summary.model.Summary;
import com.munjzservice.summary.viewmodel.SummaryVM;
import com.munjzservice.tab.active.model.ServiceRequest;

import java.util.Observable;
import java.util.Observer;

public class SummaryActivity extends AppCompatActivity implements Observer {

    ActivitySummaryBinding binding;
    SummaryVM summaryVM;

    ServiceRequest serviceRequest=new ServiceRequest();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentData();
        bindView();
        setUpObserver(summaryVM);
    }
    private void getIntentData(){
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            serviceRequest=(ServiceRequest) bundle.getSerializable("serviceRequest");
        }
    }
    private void bindView(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_summary);
        summaryVM=new SummaryVM(this,serviceRequest);
        binding.setSummaryVM(summaryVM);
    }

    public void setUpObserver(Observable observable) {
        observable.addObserver(this);
    }

    private void setupRecyclerView(Summary summary){
        if (summary.getOptions().size()!=0) {
            SummaryAdapter adapter = new SummaryAdapter(this, summary.getOptions().get(0).getValues());
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
            binding.recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void update(Observable observable, Object object) {
        if(observable instanceof SummaryVM) {
            binding.setSummary(summaryVM.getSummary());
            setupRecyclerView(summaryVM.getSummary());
        }
    }
}

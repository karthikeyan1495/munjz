package com.munjzservice.servicerequest;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.munjzservice.R;
import com.munjzservice.SplashActivity;
import com.munjzservice.databinding.ActivityViewServiceRequestBinding;
import com.munjzservice.location.CustomInfoWindow;
import com.munjzservice.login.LoginActivity;
import com.munjzservice.servicerequest.model.Price;
import com.munjzservice.servicerequest.viewmodel.EditServiceRequestVM;
import com.munjzservice.servicerequest.viewmodel.ViewServiceRequestVM;
import com.munjzservice.sharedpreferences.AppSession;
import com.munjzservice.tab.MainActivity;
import com.munjzservice.tab.active.model.ServiceRequest;
import com.munjzservice.utility.StringUtility;
import com.munjzservice.utility.WorkaroundMapFragment;

import java.util.Observable;
import java.util.Observer;

public class ViewServiceRequestActivity extends AppCompatActivity implements Observer, OnMapReadyCallback {

    ActivityViewServiceRequestBinding binding;
    ViewServiceRequestVM viewServiceRequestVM;
    ServiceRequest serviceRequest;
    GoogleMap googleMap=null;
    String serviceRequestId="S1518540076";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentData();
        checkLoginStatus();
    }

    private void getIntentData(){
        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            serviceRequestId=bundle.getString("serviceRequestId").trim();
        }
    }

    private void checkLoginStatus(){
        if (AppSession.getInstance(this).isLogin()) {
            if (serviceRequestId!=null) {
                initBinding();
                setUpObserver(viewServiceRequestVM);
                setupGoogleMap();

            }else {
                finish();
            }
        }else{
            startActivity(new Intent(ViewServiceRequestActivity.this, LoginActivity.class));
            finish();
        }
    }
    private void initBinding(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_view_service_request);
        viewServiceRequestVM=new ViewServiceRequestVM(this,serviceRequestId);
        binding.setViewServiceRequestVM(viewServiceRequestVM);
        serviceRequest=new ServiceRequest();
        serviceRequest.setStatus("Request");
        binding.setServiceRequest(serviceRequest);

        setSupportActionBar(binding.toolbar);

        ((WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).setListener(new WorkaroundMapFragment.OnTouchListener() {
            @Override
            public void onTouch() {
                binding.scrollView.requestDisallowInterceptTouchEvent(true);
            }
        });
    }
    private void setupGoogleMap() {
        WorkaroundMapFragment mapFragment = (WorkaroundMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    public void setUpObserver(Observable observable) {
        observable.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object object) {
        if(observable instanceof  ViewServiceRequestVM) {
         serviceRequest=viewServiceRequestVM.getServiceRequest();
         if (serviceRequest!=null){
             binding.setServiceRequest(serviceRequest);
             updateGoogleMap();
         }else {
             finish();
         }
        }
    }
    private void updateGoogleMap(){
        if (googleMap!=null) {
            if (serviceRequest.getLatitude() != null && serviceRequest.getLongitude() != null) {
                double latitude = Double.valueOf(serviceRequest.getLatitude());
                double longitide = Double.valueOf(serviceRequest.getLongitude());
                LatLng latLng = new LatLng(latitude, longitide);
                googleMap.addMarker(new MarkerOptions().position(latLng).snippet(serviceRequest.getTeams_appointment_location()));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
                googleMap.setInfoWindowAdapter(new CustomInfoWindow(this));
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap=googleMap;
    }
}

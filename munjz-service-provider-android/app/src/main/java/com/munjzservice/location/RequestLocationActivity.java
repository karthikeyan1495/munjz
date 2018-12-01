package com.munjzservice.location;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.munjzservice.R;
import com.munjzservice.databinding.ActivityRequestLocationBinding;
import com.munjzservice.location.viewmodel.RequestLocationVM;
import com.munjzservice.tab.active.model.ServiceRequest;

public class RequestLocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    ActivityRequestLocationBinding binding;
    RequestLocationVM requestLocationVM;
    ServiceRequest serviceRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentData();
        initDataBinding();
        setupGoogleMap();
    }

    private void initDataBinding(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_request_location);
        requestLocationVM=new RequestLocationVM(this);
        binding.setRequestLocationVM(requestLocationVM);
    }
    private void setupGoogleMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void getIntentData(){
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            serviceRequest=(ServiceRequest) bundle.getSerializable("serviceRequest");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (serviceRequest.getLatitude()!=null&&serviceRequest.getLongitude()!=null) {
            double latitude=Double.valueOf(serviceRequest.getLatitude());
            double longitide=Double.valueOf(serviceRequest.getLongitude());
            LatLng latLng = new LatLng(latitude, longitide);
            googleMap.addMarker(new MarkerOptions().position(latLng).snippet(serviceRequest.getTeams_appointment_location()));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
            googleMap.setInfoWindowAdapter(new CustomInfoWindow(this));
        }
    }
}

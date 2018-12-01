package com.munjzservice.tab;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.munjzservice.R;
import com.munjzservice.databinding.ActivityMainBinding;
import com.munjzservice.databinding.CustomTabBinding;
import com.munjzservice.databinding.NavHeaderMainBinding;
import com.munjzservice.profile.model.Customer;
import com.munjzservice.sharedpreferences.AppSession;
import com.munjzservice.tab.active.model.ActiveModel;
import com.munjzservice.tab.active.model.ServiceRequest;
import com.munjzservice.tab.adapter.TabViewPagerAdapter;
import com.munjzservice.tab.model.TabInfo;
import com.munjzservice.tab.viewmodel.CustomTabVM;
import com.munjzservice.tab.viewmodel.MainActivityVM;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,OnTabCountChangeListener {

    ActivityMainBinding binding;
    NavHeaderMainBinding headerMainBinding;

    MainActivityVM mainActivityVM;

    CustomTabBinding tabBinding1;
    CustomTabBinding tabBinding2;
    CustomTabBinding tabBinding3;

    public boolean isFirstLoad=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();
    }

    private void bindView(){
        binding= DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainActivityVM=new MainActivityVM(this);
        binding.setMainVM(mainActivityVM);
        Customer customer= AppSession.getInstance(this).getCustomerInfo();
        binding.setCustomer(customer);
        headerMainBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.nav_header_main, binding.navView, false);
        setSupportActionBar(binding.toolbar);
        setupNavigationView();
        setupViewPager(binding.viewpager);
        binding.viewpager.setOffscreenPageLimit(3);
    }

    private void setupNavigationView(){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        binding.navView.setNavigationItemSelectedListener(this);
        toggle.setDrawerIndicatorEnabled(false);
        binding.toggleAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    binding.drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        TabViewPagerAdapter adapter = new TabViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        binding.tabs.setupWithViewPager(viewPager);
        setupCustomTab("0","0","0",false);
    }

    private void setupCustomTab(String active_count,String accepted_count,String history_count,boolean isUpdate) {

        if (!isUpdate) {
            tabBinding1 = DataBindingUtil.inflate(getLayoutInflater(), R.layout.custom_tab, null, false);
            tabBinding1.setTabInfo(new TabInfo(getString(R.string.active), active_count));
            tabBinding1.setCustomTabVM(new CustomTabVM(this));
        }else {
            tabBinding1.setTabInfo(new TabInfo(getString(R.string.active), active_count));
        }

        if (!isUpdate) {
            tabBinding2 = DataBindingUtil.inflate(getLayoutInflater(), R.layout.custom_tab, null, false);
            tabBinding2.setTabInfo(new TabInfo(getString(R.string.accepted), accepted_count));
            tabBinding2.setCustomTabVM(new CustomTabVM(this));
        }else {
            tabBinding2.setTabInfo(new TabInfo(getString(R.string.accepted), accepted_count));
        }

        if (!isUpdate) {
            tabBinding3 = DataBindingUtil.inflate(getLayoutInflater(), R.layout.custom_tab, null, false);
            tabBinding3.setTabInfo(new TabInfo(getString(R.string.history), history_count));
            tabBinding3.setCustomTabVM(new CustomTabVM(this));
        }else {
            tabBinding3.setTabInfo(new TabInfo(getString(R.string.history), history_count));
        }
        if (!isUpdate) {
            binding.tabs.getTabAt(0).setCustomView(tabBinding1.getRoot());
            binding.tabs.getTabAt(1).setCustomView(tabBinding2.getRoot());
            binding.tabs.getTabAt(2).setCustomView(tabBinding3.getRoot());
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void tabCountChanged(ActiveModel activeModel) {
        setupCustomTab(String.valueOf(activeModel.getRequestcount()),String.valueOf(activeModel.getAcceptcount()),String.valueOf(activeModel.getHistorycount()),true);
    }

}

package com.munjzservice.tab.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.munjzservice.tab.accepted.AcceptedListFragment;
import com.munjzservice.tab.active.ActiveListFragment;
import com.munjzservice.tab.history.HistoryFragment;

/**
 * Created by mac on 12/11/17.
 */

public class TabViewPagerAdapter extends FragmentPagerAdapter {

    public TabViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        if (position==0){
            fragment=new ActiveListFragment();
        }else if(position==1) {
            fragment=new AcceptedListFragment();
        }else{
            fragment=new HistoryFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

}

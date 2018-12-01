package com.munjzservice.tab.active;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.munjzservice.R;
import com.munjzservice.databinding.FragmentActiveListBinding;
import com.munjzservice.tab.MainActivity;
import com.munjzservice.tab.OnTabCountChangeListener;
import com.munjzservice.tab.active.adapter.ActiveItemAdapter;
import com.munjzservice.tab.active.model.ActiveModel;
import com.munjzservice.tab.active.viewmodel.ActiveListVM;
import com.munjzservice.utility.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActiveListFragment extends Fragment implements Observer {


    FragmentActiveListBinding binding;
    ActiveListVM activeListVM;

    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
    ActiveItemAdapter adapter;

    OnTabCountChangeListener onTabCountChangeListener=null;

    public ActiveListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initBinding(inflater, container);
        setupRecyclerView();
        setUpObserver(activeListVM);
        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity;
        if (context instanceof Activity){
            activity=(Activity) context;
            if (activity instanceof MainActivity){
                onTabCountChangeListener=(OnTabCountChangeListener) activity;
            }
        }

    }

    private void initBinding(LayoutInflater inflater, ViewGroup container){
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_active_list,container,false);
        activeListVM=new ActiveListVM(getActivity());
        binding.setActiveListVM(activeListVM);

    }
    private void setupRecyclerView(){
        adapter=new ActiveItemAdapter(getActivity());
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);
       if (isFirstLoad()){
           activeListVM.callAPI();
           if (getActivity() instanceof MainActivity){
               MainActivity mainActivity=(MainActivity)getActivity();
               mainActivity.isFirstLoad =false;
           }
       }

    }


    EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener=new EndlessRecyclerOnScrollListener(linearLayoutManager) {
        @Override
        public void onLoadMore(int current_page) {
            activeListVM.loadMoreVideo(current_page);
        }
    };



    public void setUpObserver(Observable observable) {
        observable.addObserver(this);
    }

    private boolean isFirstLoad(){
        if (getActivity() instanceof MainActivity){
            MainActivity mainActivity=(MainActivity)getActivity();
            return mainActivity.isFirstLoad;
        }
        return true;
    }

    @Override
    public void update(Observable observable, Object object) {
        if(observable instanceof  ActiveListVM) {
            adapter.setData(activeListVM.getActiveModel(),false);
            if (onTabCountChangeListener!=null){
                onTabCountChangeListener.tabCountChanged(activeListVM.getActiveModel());
                adapter.setTabCountChangeListener(onTabCountChangeListener);
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser&&!isFirstLoad()) {
            adapter.clearData();
            endlessRecyclerOnScrollListener.resetDefualt();
            activeListVM.callAPI();
        }
    }

}

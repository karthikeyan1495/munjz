package com.munjzservice.tab.accepted;


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
import com.munjzservice.databinding.FragmentAcceptedListBinding;
import com.munjzservice.databinding.FragmentActiveListBinding;
import com.munjzservice.tab.MainActivity;
import com.munjzservice.tab.OnTabCountChangeListener;
import com.munjzservice.tab.accepted.adapter.AcceptedItemAdapter;
import com.munjzservice.tab.accepted.viewmodel.AcceptedItemVM;
import com.munjzservice.tab.accepted.viewmodel.AcceptedListVM;
import com.munjzservice.tab.active.adapter.ActiveItemAdapter;
import com.munjzservice.tab.active.model.ActiveModel;
import com.munjzservice.tab.active.viewmodel.ActiveItemVM;
import com.munjzservice.tab.active.viewmodel.ActiveListVM;
import com.munjzservice.utility.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * A simple {@link Fragment} subclass.
 */

public class AcceptedListFragment extends Fragment implements Observer {

    FragmentAcceptedListBinding binding;
    AcceptedListVM acceptedListVM;

    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
    AcceptedItemAdapter adapter;
    OnTabCountChangeListener onTabCountChangeListener=null;


    public AcceptedListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initBinding(inflater,container);
        setupRecyclerView();
        setUpObserver(acceptedListVM);
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
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_accepted_list,container,false);
        acceptedListVM=new AcceptedListVM(getActivity());
        binding.setAcceptedListVM(acceptedListVM);
    }

    private void setupRecyclerView(){
        adapter=new AcceptedItemAdapter(getActivity(),this);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);

    }
    EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener=new EndlessRecyclerOnScrollListener(linearLayoutManager) {
        @Override
        public void onLoadMore(int current_page) {
            acceptedListVM.loadMoreVideo(current_page);
        }
    };
    public void setUpObserver(Observable observable) {
        observable.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object object) {
        if(observable instanceof  AcceptedListVM) {
            adapter.setData(acceptedListVM.getActiveModel().getList(),false);
            if (onTabCountChangeListener!=null){
                onTabCountChangeListener.tabCountChanged(acceptedListVM.getActiveModel());
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            refreshData();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==Activity.RESULT_OK&&requestCode== AcceptedItemVM.EDIT_REQUEST){
            if (data.getBooleanExtra("isUpdated",false)){
                refreshData();
            }
        }
    }

    private void refreshData(){
        adapter.clearData();
        endlessRecyclerOnScrollListener.resetDefualt();
        acceptedListVM.callAPI();
    }

}

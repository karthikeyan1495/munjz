package com.munjzservice.tab.history;


import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.munjzservice.R;
import com.munjzservice.databinding.FragmentAcceptedListBinding;
import com.munjzservice.databinding.FragmentHistoryBinding;
import com.munjzservice.tab.MainActivity;
import com.munjzservice.tab.OnTabCountChangeListener;
import com.munjzservice.tab.accepted.adapter.AcceptedItemAdapter;
import com.munjzservice.tab.accepted.viewmodel.AcceptedListVM;
import com.munjzservice.tab.history.adapter.HistoryItemAdapter;
import com.munjzservice.tab.history.viewmodel.HistoryVM;
import com.munjzservice.utility.EndlessRecyclerOnScrollListener;

import java.util.Observable;
import java.util.Observer;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment implements Observer {


    FragmentHistoryBinding binding;
    HistoryVM historyVM;
    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
    HistoryItemAdapter adapter;

    OnTabCountChangeListener onTabCountChangeListener=null;


    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initBinding(inflater,container);
        setupRecyclerView();
        setUpObserver(historyVM);

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
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_history,container,false);
        historyVM=new HistoryVM(getActivity());
        binding.setHistoryVM(historyVM);

    }
    private void setupRecyclerView(){
        adapter=new HistoryItemAdapter(getActivity());
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);
    }

    EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener=new EndlessRecyclerOnScrollListener(linearLayoutManager) {
        @Override
        public void onLoadMore(int current_page) {
            historyVM.loadMoreVideo(current_page);
        }
    };


    public void setUpObserver(Observable observable) {
        observable.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object object) {
        if(observable instanceof  HistoryVM) {
            adapter.setData(historyVM.getActiveModel().getList(),false);
            if (onTabCountChangeListener!=null){
                onTabCountChangeListener.tabCountChanged(historyVM.getActiveModel());
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            adapter.clearData();
            endlessRecyclerOnScrollListener.resetDefualt();
            historyVM.callAPI();
        }
    }

}

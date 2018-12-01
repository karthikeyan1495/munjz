package com.munjzservice.tab.active.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.munjzservice.R;
import com.munjzservice.databinding.ActiveItemBinding;
import com.munjzservice.tab.OnTabCountChangeListener;
import com.munjzservice.tab.Status;
import com.munjzservice.tab.active.model.ActiveModel;
import com.munjzservice.tab.active.model.RequestAction;
import com.munjzservice.tab.active.model.ServiceRequest;
import com.munjzservice.tab.active.viewmodel.ActiveItemVM;
import com.munjzservice.tab.active.viewmodel.ActiveListVM;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by mac on 12/12/17.
 */

public class ActiveItemAdapter extends RecyclerView.Adapter<ActiveItemAdapter.ViewHolder> implements Observer {

    private List<ServiceRequest> list = new ArrayList<>();
    Activity activity;
    OnTabCountChangeListener onTabCountChangeListener=null;
    ActiveModel activeModel;
    public ActiveItemAdapter(@NonNull Activity activity) {
        this.activity=activity;
    }

    @Override
    public ActiveItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ActiveItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.active_item, parent, false);
        return new ActiveItemAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ActiveItemAdapter.ViewHolder holder, int position) {
        ServiceRequest serviceRequest = list.get(position);
        holder.bind(serviceRequest);
        setUpObserver(holder.activeItemVM);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setTabCountChangeListener(OnTabCountChangeListener onTabCountChangeListener){
        this.onTabCountChangeListener=onTabCountChangeListener;
    }

    public void setData(ActiveModel activeModel,boolean isClearOldRecord){
        this.activeModel=activeModel;

        if (isClearOldRecord) {
            this.list.clear();
        }
        this.list.addAll(this.activeModel.getList());
        notifyDataSetChanged();
    }

    public void clearData(){
        this.list.clear();
        notifyDataSetChanged();
    }

    @Override
    public void update(Observable observable, Object object) {
        if (observable instanceof ActiveItemVM) {
            RequestAction requestAction=(RequestAction)object;
            list.remove(requestAction.getServiceRequest());
            notifyDataSetChanged();
            if (onTabCountChangeListener!=null){
                if (requestAction.getAction()==Status.ACCEPT){
                    activeModel.setAcceptcount(activeModel.getAcceptcount()+1);
                }else{
                    activeModel.setHistorycount(activeModel.getHistorycount()+1);
                }
                activeModel.setRequestcount(activeModel.getRequestcount()-1);
                onTabCountChangeListener.tabCountChanged(activeModel);
            }
        }
    }

    public void setUpObserver(Observable observable) {
        observable.addObserver(this);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ActiveItemBinding binding;
        ActiveItemVM activeItemVM;
        public ViewHolder(ActiveItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(@NonNull ServiceRequest serviceRequest) {
            activeItemVM=new ActiveItemVM(activity);
            binding.setActiveItemVM(activeItemVM);
            binding.setServiceRequest(serviceRequest);
            binding.executePendingBindings();
        }
    }
}

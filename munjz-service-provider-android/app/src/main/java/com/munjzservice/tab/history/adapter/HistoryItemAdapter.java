package com.munjzservice.tab.history.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.munjzservice.R;
import com.munjzservice.databinding.HistoryItemBinding;
import com.munjzservice.tab.active.model.ServiceRequest;
import com.munjzservice.tab.history.viewmodel.HistoryItemVM;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 12/16/17.
 */

public class HistoryItemAdapter extends RecyclerView.Adapter<HistoryItemAdapter.ViewHolder> {

    private List<ServiceRequest> list = new ArrayList<>();
    Activity activity;


    public HistoryItemAdapter(@NonNull Activity activity) {
        this.activity=activity;
    }

    @Override
    public HistoryItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        HistoryItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.history_item, parent, false);
        return new HistoryItemAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(HistoryItemAdapter.ViewHolder holder, int position) {
        ServiceRequest serviceRequest = list.get(position);
        holder.bind(serviceRequest);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<ServiceRequest>serviceRequests,boolean isClearOldRecord){
        if (isClearOldRecord) {
            this.list.clear();
        }
        this.list.addAll(serviceRequests);
        notifyDataSetChanged();
    }
    public void clearData(){
        this.list.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private HistoryItemBinding binding;
        public ViewHolder(HistoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(@NonNull ServiceRequest serviceRequest) {
            binding.setHistoryItemVM(new HistoryItemVM(activity));
            binding.setServiceRequest(serviceRequest);
            binding.executePendingBindings();
        }
    }
}

package com.munjzservice.tab.accepted.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.munjzservice.R;
import com.munjzservice.databinding.AcceptedItemBinding;
import com.munjzservice.databinding.ActiveItemBinding;
import com.munjzservice.databinding.AdditionalServiceViewBinding;
import com.munjzservice.tab.accepted.viewmodel.AcceptedItemVM;
import com.munjzservice.tab.active.model.ActiveModel;
import com.munjzservice.tab.active.model.ServiceRequest;
import com.munjzservice.tab.active.viewmodel.ActiveItemVM;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 12/12/17.
 */

public class AcceptedItemAdapter extends RecyclerView.Adapter<AcceptedItemAdapter.ViewHolder> {

    private List<ServiceRequest> list = new ArrayList<>();
    Activity activity;
    Fragment fragment;

    public AcceptedItemAdapter(@NonNull Activity activity,Fragment fragment) {
        this.activity=activity;
        this.fragment=fragment;
    }

    @Override
    public AcceptedItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        AcceptedItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.accepted_item, parent, false);
        return new AcceptedItemAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(AcceptedItemAdapter.ViewHolder holder, int position) {
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
        private AcceptedItemBinding binding;
        public ViewHolder(AcceptedItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(@NonNull ServiceRequest serviceRequest) {
            binding.setAcceptedItemVM(new AcceptedItemVM(activity,fragment));
            binding.setServiceRequest(serviceRequest);
           /* binding.additionalServiceView.removeAllViews();
            for (int i=0;i<serviceRequest.getAdditional_service().size();i++){
                AdditionalServiceViewBinding viewBinding=DataBindingUtil.inflate(activity.getLayoutInflater(), R.layout.additional_service_view, binding.additionalServiceView, false);
                viewBinding.setAdditionalService(serviceRequest.getAdditional_service().get(i));
                binding.additionalServiceView.addView(viewBinding.getRoot());
            }*/
            binding.executePendingBindings();
        }
    }
}

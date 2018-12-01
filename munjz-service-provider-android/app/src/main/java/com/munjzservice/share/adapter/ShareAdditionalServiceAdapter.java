package com.munjzservice.share.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.munjzservice.R;
import com.munjzservice.databinding.ShareAdditionalServiceItemBinding;
import com.munjzservice.share.viewmodel.ShareAdditionalServiceItemVM;
import com.munjzservice.tab.active.model.AdditionalService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2/5/18.
 */

public class ShareAdditionalServiceAdapter extends RecyclerView.Adapter<ShareAdditionalServiceAdapter.ViewHolder>{

    Activity activity;

    List<AdditionalService> list=new ArrayList<>();

    public ShareAdditionalServiceAdapter(Activity activity,List<AdditionalService> list){
        this.activity=activity;
        this.list=list;
    }

    @Override
    public ShareAdditionalServiceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ShareAdditionalServiceItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.share_additional_service_item, parent, false);
        return new ShareAdditionalServiceAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ShareAdditionalServiceAdapter.ViewHolder holder, int position) {
        AdditionalService serviceRequest = list.get(position);
        holder.bind(serviceRequest);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ShareAdditionalServiceItemBinding binding;
        public ViewHolder(ShareAdditionalServiceItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(@NonNull AdditionalService additionalService) {
            binding.setShareAdditionalServiceItemVM(new ShareAdditionalServiceItemVM(activity));
            binding.setAdditionalService(additionalService);
            binding.executePendingBindings();
        }
    }
}

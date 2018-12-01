package com.munjzservice.servicerequest.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.munjzservice.R;
import com.munjzservice.databinding.AdditionalServiceItemBinding;
import com.munjzservice.servicerequest.EditServiceRequestActivity;
import com.munjzservice.servicerequest.delegate.AdditionalServiceListener;
import com.munjzservice.servicerequest.viewmodel.AdditionalServiceItem;
import com.munjzservice.tab.active.model.AdditionalService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2/4/18.
 */

public class AdditionalServiceAdapter extends RecyclerView.Adapter<AdditionalServiceAdapter.ViewHolder> implements AdditionalServiceListener {

    Activity activity;

    List<AdditionalService> list=new ArrayList<>();

    public AdditionalServiceAdapter(Activity activity,List<AdditionalService> list){
        this.activity=activity;
        this.list=list;
    }

    @Override
    public AdditionalServiceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        AdditionalServiceItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.additional_service_item, parent, false);
        return new AdditionalServiceAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(AdditionalServiceAdapter.ViewHolder holder, int position) {
        AdditionalService serviceRequest = list.get(position);
        holder.bind(serviceRequest,this);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAdditionalService(){
        AdditionalService additionalService=new AdditionalService();
        //additionalService.setService_name("Name "+list.size()+1);
        //additionalService.setPrice(""+list.size()+1);
        list.add(additionalService);
        notifyDataSetChanged();
    }

    private void deleteAdditionalService(AdditionalService additionalService){
        list.remove(additionalService);
        notifyDataSetChanged();
        if (activity instanceof EditServiceRequestActivity){
            EditServiceRequestActivity editServiceRequestActivity=(EditServiceRequestActivity)activity;
            if (!additionalService.getPrice().trim().equals("")){
                try {
                    float value = Float.valueOf(additionalService.getPrice().trim());
                    editServiceRequestActivity.minusPrice(value);
                }catch (Exception e){
                    editServiceRequestActivity.minusPrice(0);
                }
            }
        }


    }

    @Override
    public void onAdditionalServiceDeleted(View view, AdditionalService additionalService) {
        deleteAdditionalService(additionalService);
    }

    @Override
    public void onAdditionalServicePriceChanged(float newValue,AdditionalService additionalService) {
        if (activity instanceof EditServiceRequestActivity){
            EditServiceRequestActivity editServiceRequestActivity=(EditServiceRequestActivity)activity;
            float oldValue=0;
            if (!additionalService.getPrice().trim().equals("")){
                try {
                    oldValue = Float.valueOf(additionalService.getPrice().trim());
                }catch (Exception e){
                }
            }
            editServiceRequestActivity.updatePrice(oldValue,newValue);
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private AdditionalServiceItemBinding binding;
        public ViewHolder(AdditionalServiceItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(@NonNull AdditionalService additionalService,AdditionalServiceListener listener) {
            binding.setAdditionalServiceItem(new AdditionalServiceItem(activity,listener));
            binding.setAdditionalService(additionalService);
            binding.executePendingBindings();
        }
    }

}

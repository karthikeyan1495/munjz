package com.munjzservice.summary.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.munjzservice.R;
import com.munjzservice.databinding.ShareAdditionalServiceItemBinding;
import com.munjzservice.databinding.SummaryItemBinding;
import com.munjzservice.share.adapter.ShareAdditionalServiceAdapter;
import com.munjzservice.share.viewmodel.ShareAdditionalServiceItemVM;
import com.munjzservice.summary.model.Values;
import com.munjzservice.summary.viewmodel.SummaryItemVM;
import com.munjzservice.tab.active.model.AdditionalService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2/25/18.
 */

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.ViewHolder>{

    Activity activity;

    List<Values> list=new ArrayList<>();

    public SummaryAdapter(Activity activity,List<Values> list){
        this.activity=activity;
        this.list=list;
    }

    @Override
    public SummaryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SummaryItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.summary_item, parent, false);
        return new SummaryAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(SummaryAdapter.ViewHolder holder, int position) {
        Values values = list.get(position);
        holder.bind(values);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private SummaryItemBinding binding;
        public ViewHolder(SummaryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(@NonNull Values values) {
            binding.setSummaryItemVM(new SummaryItemVM(activity));
            binding.setValues(values);
            binding.executePendingBindings();
        }
    }

}

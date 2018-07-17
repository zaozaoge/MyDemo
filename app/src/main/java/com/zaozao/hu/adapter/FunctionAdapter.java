package com.zaozao.hu.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.zaozao.hu.BR;
import com.zaozao.hu.databinding.ItemFunctionBinding;
import com.zaozao.hu.module.model.FunctionItem;
import com.zaozao.hu.module.view.BaseActivity;
import com.zaozao.hu.module.viewModel.MyHandler;

import java.util.List;

/**
 * Created by 胡章孝
 * Date:2018/7/4
 * Describle:
 */
public class FunctionAdapter extends RecyclerView.Adapter<FunctionAdapter.FunctionHolder> {

    private List<FunctionItem> functionItems;
    private BaseActivity activity;

    public FunctionAdapter(BaseActivity activity, List<FunctionItem> functionItems) {
        this.functionItems = functionItems;
        this.activity = activity;
    }

    @NonNull
    @Override
    public FunctionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return FunctionHolder.getHolder(LayoutInflater.from(parent.getContext()), parent, activity);
    }

    @Override
    public void onBindViewHolder(@NonNull FunctionHolder holder, int position) {
        holder.bindTo(functionItems.get(position));
    }

    @Override
    public int getItemCount() {
        return functionItems == null ? 0 : functionItems.size();
    }

    static class FunctionHolder extends RecyclerView.ViewHolder {

        private ItemFunctionBinding binding;

        static FunctionHolder getHolder(LayoutInflater inflater, ViewGroup parent, BaseActivity activity) {
            ItemFunctionBinding binding = ItemFunctionBinding.inflate(inflater, parent, false);
            binding.setVariable(BR.handler, new MyHandler(activity));
            return new FunctionHolder(binding);
        }

        private FunctionHolder(ItemFunctionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bindTo(FunctionItem item) {
            binding.setFunctionItem(item);
            binding.executePendingBindings();
        }
    }
}

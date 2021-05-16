package com.example.matchmakingdemo.ui.adapters;


import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.matchmakingdemo.R;
import com.example.matchmakingdemo.databinding.ProgressDailogLayoutBinding;

public class LoadingViewHolder extends RecyclerView.ViewHolder {
    Context context;
    ProgressDailogLayoutBinding binding;

    public LoadingViewHolder(@NonNull View itemView, Context context, ProgressDailogLayoutBinding binding) {
        super(itemView);
        this.context = context;
        this.binding = binding;
    }

    public void onBind() {
        Glide.with(context).asGif().load(R.raw.loading).into(binding.ivProgress);
    }
}

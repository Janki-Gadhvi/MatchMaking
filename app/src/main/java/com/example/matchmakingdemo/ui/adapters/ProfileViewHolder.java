package com.example.matchmakingdemo.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.matchmakingdemo.R;
import com.example.matchmakingdemo.databinding.RvProfileListItemBinding;
import com.example.matchmakingdemo.data_source.store.models.Profile;
import com.example.matchmakingdemo.util.Constants;

public class ProfileViewHolder extends RecyclerView.ViewHolder {

    RvProfileListItemBinding binding;
    OnProfileSelectionListener onProfileSelectionListener;
    RequestManager requestManager;
    Context context;


    public ProfileViewHolder(@NonNull View itemView,
                             OnProfileSelectionListener onProfileSelectionListener,
                             RvProfileListItemBinding binding,
                             RequestManager requestManager, Context context) {
        super(itemView);
        this.binding = binding;
        this.context = context;
        this.onProfileSelectionListener = onProfileSelectionListener;
        this.requestManager = requestManager;
    }


    @SuppressLint("SetTextI18n")
    public void onBind(Profile profile, int position) {

        requestManager
                .load(profile.getPicture().getLarge())
                .into(binding.ivProfile);

        binding.tvName.setText(profile.getName().getTitle() + " " + profile.getName().getFirst() + " " + profile.getName().getLast());
        String gender = profile.getGender().substring(0, 1).toUpperCase() + profile.getGender().substring(1).toLowerCase();
        binding.tvAge.setText(profile.getDob().getAge() + "yrs • " + gender);
        binding.tvLocation.setText(profile.getLocation().getCity() + ", " + profile.getLocation().getState() + ", " + profile.getLocation().getCountry());

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        Glide.with(itemView.getContext())
                .applyDefaultRequestOptions(requestOptions)
                .load(profile.getPicture().getLarge())
                .transform(new CenterCrop(), new RoundedCorners(50))
                .into(binding.ivProfile);

        binding.btnAccept.setOnClickListener(v -> onProfileSelectionListener.onSelectionClick(position, true, profile));


        binding.btnReject.setOnClickListener(v -> onProfileSelectionListener.onSelectionClick(position, false, profile));


        switch (profile.getUser_selection_status()) {
            case Constants.SELECTED:
                binding.btnAccept.setVisibility(View.GONE);
                binding.btnReject.setVisibility(View.GONE);
                binding.btnMsg.setVisibility(View.VISIBLE);
                binding.btnMsg.setBackgroundColor(ContextCompat.getColor(context, R.color.selected_main));
                binding.btnMsg.setText(context.getString(R.string.selected));
                break;

            case Constants.REJECTED:
                binding.btnAccept.setVisibility(View.GONE);
                binding.btnReject.setVisibility(View.GONE);
                binding.btnMsg.setVisibility(View.VISIBLE);
                binding.btnMsg.setBackgroundColor(ContextCompat.getColor(context, R.color.rejected_main));
                binding.btnMsg.setText(context.getString(R.string.rejected));
                break;

            case Constants.NO_ACTION:
                binding.btnAccept.setVisibility(View.VISIBLE);
                binding.btnReject.setVisibility(View.VISIBLE);
                binding.btnMsg.setVisibility(View.GONE);

                break;
        }

    }


}

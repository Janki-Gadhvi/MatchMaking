package com.example.matchmakingdemo.ui.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.example.matchmakingdemo.databinding.ProgressDailogLayoutBinding;
import com.example.matchmakingdemo.databinding.RvProfileListItemBinding;
import com.example.matchmakingdemo.data_source.store.models.Profile;

import java.util.ArrayList;
import java.util.List;

public class ProfieRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "ProfieRecyclerAdapter";
    private List<Profile> profiles;
    private final OnProfileSelectionListener onProfileSelectionListener;
    RequestManager requestManager;

    private static final int PROFILE_TYPE = 1;
    private static final int LOADING_TYPE = 2;

    public ProfieRecyclerAdapter(OnProfileSelectionListener onProfileSelectionListener, RequestManager requestManager) {
        this.onProfileSelectionListener = onProfileSelectionListener;
        this.requestManager = requestManager;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {

            case LOADING_TYPE: {
                ProgressDailogLayoutBinding progressDailogLayoutBinding =
                        ProgressDailogLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),
                                parent, false);
                return new LoadingViewHolder(progressDailogLayoutBinding.getRoot(),
                        parent.getContext(), progressDailogLayoutBinding);
            }

            case PROFILE_TYPE:
            default: {
                RvProfileListItemBinding rvProfileListItemBinding =
                        RvProfileListItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                                parent, false);

                return new ProfileViewHolder(rvProfileListItemBinding.getRoot(),
                        onProfileSelectionListener, rvProfileListItemBinding, requestManager, parent.getContext());
            }
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == PROFILE_TYPE) {
            ((ProfileViewHolder) holder).onBind(profiles.get(position), position);
        } else if (itemViewType == LOADING_TYPE) {
            ((LoadingViewHolder) holder).onBind();
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (isTitleEmpty(position) && profiles.get(position).getTitle_status().equals("LOADING...")) {
            return LOADING_TYPE;
        } else {
            return PROFILE_TYPE;
        }
    }


    public void hideLoading() {
        if (isLoading()) {
            if (isTitleEmpty(0)
                    && profiles.get(0).getTitle_status().equals("LOADING...")) {
                profiles.remove(0);
            } else if (isTitleEmpty(profiles.size() - 1)
                    && profiles.get(profiles.size() - 1).getTitle_status().equals("LOADING...")) {
                profiles.remove(profiles.size() - 1);
            }
            notifyDataSetChanged();
        }
    }

    // pagination loading
    public void displayLoading() {
        if (profiles == null)
            profiles = new ArrayList<>();

        if (!isLoading()) {
            Profile profile = new Profile();
            profile.setTitle_status("LOADING...");
            profiles.add(profile);
            notifyDataSetChanged();
        }
    }

    private boolean isLoading() {
        if (profiles != null) {
            if (profiles.size() > 0) {
                if (isTitleEmpty(profiles.size() - 1) &&
                        profiles.get(profiles.size() - 1).getTitle_status().equals("LOADING...")) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int getItemCount() {
        if (profiles != null)
            return profiles.size();

        return 0;
    }

    public void setProfile(List<Profile> profiles) {
        this.profiles = profiles;
        notifyDataSetChanged();
    }

    public boolean isTitleEmpty(int pos) {
        return !TextUtils.isEmpty(profiles.get(pos).getTitle_status());
    }


    public void updateUserSelectionOnUI(int position, String value_to_be_updated_on_ui) {
        profiles.get(position).setUser_selection_status(value_to_be_updated_on_ui);
        notifyDataSetChanged();
    }

}

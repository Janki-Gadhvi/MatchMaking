package com.example.matchmakingdemo.ui.activities;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.matchmakingdemo.R;
import com.example.matchmakingdemo.ui.adapters.OnProfileSelectionListener;
import com.example.matchmakingdemo.ui.adapters.ProfieRecyclerAdapter;
import com.example.matchmakingdemo.databinding.ActivityProfileListBinding;
import com.example.matchmakingdemo.data_source.store.models.Profile;
import com.example.matchmakingdemo.util.Constants;
import com.example.matchmakingdemo.util.Resource;
import com.example.matchmakingdemo.util.SnapHelperOneByOne;
import com.example.matchmakingdemo.ui.viewmodels.ProfileListViewModel;
import com.example.matchmakingdemo.ui.viewmodels.ProfileViewModelFactory;

import java.util.List;

public class ProfileListActivity extends AppCompatActivity implements OnProfileSelectionListener {

    private static final String TAG = "ProfileListActivity";

    ActivityProfileListBinding binding;
    private ProfileListViewModel profileListViewModel;
    ProfieRecyclerAdapter profieRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProfileListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();

    }

    public void initViews() {
        profileListViewModel = new ViewModelProvider(this, new ProfileViewModelFactory(getApplication(), 10)).get(ProfileListViewModel.class);
        subscribeToObserve();
        setRecyclerview();
        setButtonClick();
    }

    private void setButtonClick() {
        binding.btnSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActionedProfileActivity();
            }
        });
    }

    public void openActionedProfileActivity() {
        startActivity(ActionedProfileActivity.getInstance(this));
    }

    public void subscribeToObserve() {

        profileListViewModel.getProfiles().observe(this, new Observer<Resource<List<Profile>>>() {
            @Override
            public void onChanged(Resource<List<Profile>> listResource) {
                Log.d(TAG, "onChanged: status: " + listResource.status);

                if (listResource.data != null) {
                    switch (listResource.status) {
                        case LOADING: {
                            profieRecyclerAdapter.displayLoading();
                            break;
                        }
                        case SUCCESS: {
                            Log.d(TAG, "onChanged: cache has been refreshed.");
                            Log.d(TAG, "onChanged: status: SUCCESS, #Profiles: " + listResource.data.size());
                            profieRecyclerAdapter.hideLoading();
                            profieRecyclerAdapter.setProfile(listResource.data);
                            break;
                        }
                        case ERROR: {
                            Log.e(TAG, "onChanged: cannot refresh cache.");
                            Log.e(TAG, "onChanged: ERROR message: " + listResource.message);
                            Log.e(TAG, "onChanged: status: ERROR, #Recipes: " + listResource.data.size());
                            profieRecyclerAdapter.hideLoading();
                            profieRecyclerAdapter.setProfile(listResource.data);
                            Toast.makeText(ProfileListActivity.this, listResource.message, Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
            }
        });
    }

    private RequestManager initGlide() {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        return Glide.with(this).setDefaultRequestOptions(options);
    }


    public void setRecyclerview() {
        profieRecyclerAdapter = new ProfieRecyclerAdapter(this, initGlide());
        binding.rvProfile.setAdapter(profieRecyclerAdapter);


        SnapHelperOneByOne snapHelper = new SnapHelperOneByOne();
        snapHelper.attachToRecyclerView(binding.rvProfile);


        binding.rvProfile.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!binding.rvProfile.canScrollHorizontally(1)) {
                    profileListViewModel.searchNextPage(10);
                }
            }
        });
    }

    @Override
    public void onSelectionClick(int position, boolean is_selected, Profile profile) {
        if (is_selected) {
            profileListViewModel.updateUserSelection(profile.getProfile_id(), Constants.SELECTED);
            profieRecyclerAdapter.updateUserSelectionOnUI(position, Constants.SELECTED);
        } else {
            profileListViewModel.updateUserSelection(profile.getProfile_id(), Constants.REJECTED);
            profieRecyclerAdapter.updateUserSelectionOnUI(position, Constants.REJECTED);
        }
    }
}
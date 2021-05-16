package com.example.matchmakingdemo.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.matchmakingdemo.R;
import com.example.matchmakingdemo.ui.adapters.ProfieRecyclerAdapter;
import com.example.matchmakingdemo.databinding.ActivityProfileListBinding;
import com.example.matchmakingdemo.data_source.store.models.Profile;
import com.example.matchmakingdemo.util.Resource;
import com.example.matchmakingdemo.util.SnapHelperOneByOne;
import com.example.matchmakingdemo.ui.viewmodels.ProfileListViewModel;
import com.example.matchmakingdemo.ui.viewmodels.ProfileViewModelFactory;

import java.util.List;

public class ActionedProfileActivity extends AppCompatActivity {
    private static final String TAG = "ActionedProfileActivity";

    ActivityProfileListBinding binding;
    private ProfileListViewModel profileListViewModel;
    ProfieRecyclerAdapter profieRecyclerAdapter;


    public static Intent getInstance(Context context) {
        return new Intent(context, ActionedProfileActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProfileListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();

    }

    public void initViews() {
        profileListViewModel = new ViewModelProvider(this, new ProfileViewModelFactory(getApplication(), -1)).get(ProfileListViewModel.class);
        binding.btnSeeMore.setVisibility(View.GONE);
        subscribeToObserve();
        setRecyclerview();
    }

    public void subscribeToObserve() {

        profileListViewModel.getActionedProfiles().observe(this, new Observer<Resource<List<Profile>>>() {
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

                            if (listResource.data.size() == 0) {
                                binding.tvNoDataFound.setVisibility(View.VISIBLE);
                            } else {
                                binding.tvNoDataFound.setVisibility(View.GONE);
                            }
                            break;
                        }
                        case ERROR: {
                            Log.e(TAG, "onChanged: cannot refresh cache.");
                            Log.e(TAG, "onChanged: ERROR message: " + listResource.message);
                            Log.e(TAG, "onChanged: status: ERROR, #Recipes: " + listResource.data.size());
                            profieRecyclerAdapter.hideLoading();
                            profieRecyclerAdapter.setProfile(listResource.data);

                            if (listResource.data.size() == 0) {
                                binding.tvNoDataFound.setVisibility(View.VISIBLE);
                            } else {
                                binding.tvNoDataFound.setVisibility(View.GONE);
                            }

                            Toast.makeText(ActionedProfileActivity.this, listResource.message, Toast.LENGTH_SHORT).show();
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
        profieRecyclerAdapter = new ProfieRecyclerAdapter(null, initGlide());
        binding.rvProfile.setAdapter(profieRecyclerAdapter);

        SnapHelperOneByOne snapHelper = new SnapHelperOneByOne();
        snapHelper.attachToRecyclerView(binding.rvProfile);
    }
}

package com.example.matchmakingdemo.data_source.network;

import androidx.lifecycle.MutableLiveData;

import com.example.matchmakingdemo.data_source.store.models.Profile;

import java.util.List;

public class ProfileApiClient {
    private static final String TAG = "ProfileApiClient";
    private static ProfileApiClient instance;
    private MutableLiveData<List<Profile>> mProfiles;


    public static ProfileApiClient getInstance() {

        if (instance == null) {
            instance = new ProfileApiClient();
        }
        return instance;
    }

    private ProfileApiClient() {
        mProfiles = new MutableLiveData<>();
    }
}

package com.example.matchmakingdemo.data_source.network.responses;

import com.example.matchmakingdemo.data_source.store.models.Profile;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfileListResponse {
    private static final String TAG = "ProfileListResponse";


    @SerializedName("results")
    @Expose
    private List<Profile> results;

    public List<Profile> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "ProfileListResponse{" +
                "results=" + results +
                '}';
    }
}

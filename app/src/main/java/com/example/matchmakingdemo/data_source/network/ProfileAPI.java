package com.example.matchmakingdemo.data_source.network;

import androidx.lifecycle.LiveData;

import com.example.matchmakingdemo.data_source.network.responses.ApiResponse;
import com.example.matchmakingdemo.data_source.network.responses.ProfileListResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProfileAPI {

    @GET("api/")
    LiveData<ApiResponse<ProfileListResponse>> getProfileList(@Query("results") int results);
}

package com.example.matchmakingdemo.data_source.network;

import com.example.matchmakingdemo.util.Constants;
import com.example.matchmakingdemo.util.LiveDataCallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static final String TAG = "ServiceGenerator";

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addCallAdapterFactory(new LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static ProfileAPI profileAPI = retrofit.create(ProfileAPI.class);

    public static ProfileAPI getProfileAPI(){
        return profileAPI;
    }
}

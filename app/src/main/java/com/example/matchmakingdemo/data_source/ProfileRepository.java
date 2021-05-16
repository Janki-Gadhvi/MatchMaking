package com.example.matchmakingdemo.data_source;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.matchmakingdemo.util.AppExecutors;
import com.example.matchmakingdemo.data_source.store.models.Profile;
import com.example.matchmakingdemo.data_source.store.ProfileDao;
import com.example.matchmakingdemo.data_source.store.ProfileDatabase;
import com.example.matchmakingdemo.data_source.network.ServiceGenerator;
import com.example.matchmakingdemo.data_source.network.responses.ApiResponse;
import com.example.matchmakingdemo.data_source.network.responses.ProfileListResponse;
import com.example.matchmakingdemo.util.Constants;
import com.example.matchmakingdemo.util.NetworkBoundResource;
import com.example.matchmakingdemo.util.Resource;

import java.util.List;

public class ProfileRepository {
    private static final String TAG = "ProfileRepository";
    private static ProfileRepository instance;
    private ProfileDao profileDao;


    public static ProfileRepository getInstance(Context context) {
        if (instance == null) {
            instance = new ProfileRepository(context);
        }
        return instance;
    }

    public ProfileRepository(Context context) {
        profileDao = ProfileDatabase.getInstance(context).getProfileDao();
    }


    public LiveData<Resource<List<Profile>>> getProfiles(int query, int total_count) {
        return new NetworkBoundResource<List<Profile>, ProfileListResponse>(AppExecutors.getInstance()) {
            @Override
            public void saveCallResult(@NonNull ProfileListResponse item) {
                //this will be to add data in database
                if (item.getResults() != null) {

                    Profile[] profiles = new Profile[item.getResults().size()];

                    int index = 0;

                    for (long rowId : profileDao.insertProfiles(item.getResults().toArray(profiles))) {
                        if (rowId == -1) { // conflict detected
                            Log.d(TAG, "saveCallResult: CONFLICT... This profile is already in cache.");
                            // if already exists,  timestamp b/c they will be erased
                            Profile profile = profiles[index];
                            profileDao.updateProfile(
                                    profile.getProfile_id(),
                                    profile.getGender(),
                                    profile.getName().getTitle(),
                                    profile.getName().getFirst(),
                                    profile.getName().getLast(),
                                    profile.getLocation().getCity(),
                                    profile.getLocation().getState(),
                                    profile.getLocation().getCountry(),
                                    profile.getDob().getAge()
                            );
                            index++;
                        }
                    }

                }
            }

            @Override
            public boolean shouldFetch(@Nullable List<Profile> data) {
                if (data == null || data.size() == 0)
                    return true;
                else {
                    return total_count > data.size();
                }
            }

            @NonNull
            @Override
            public LiveData<List<Profile>> loadFromDb() {
                return profileDao.getAllProfiles(Constants.NO_ACTION);
            }

            @NonNull
            @Override
            public LiveData<ApiResponse<ProfileListResponse>> createCall() {
                return ServiceGenerator.getProfileAPI().getProfileList(query);
            }
        }.getAsLiveData();
    }


    public void updateUserSelectionStatus(long profile_id, String val) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                int number_of_row = profileDao.updateUserSelectionStatus(profile_id, val);
                Log.d("number_of_row_updated", number_of_row + "");
            }
        });
    }

    public LiveData<Resource<List<Profile>>> getActionedProfiles() {
        return new NetworkBoundResource<List<Profile>, ProfileListResponse>(AppExecutors.getInstance()) {
            @Override
            public void saveCallResult(@NonNull ProfileListResponse item) {
                //this will be to add data in database
            }

            @Override
            public boolean shouldFetch(@Nullable List<Profile> data) {
                return false;
            }

            @NonNull
            @Override
            public LiveData<List<Profile>> loadFromDb() {
                return profileDao.getAllActionedProfiles(Constants.SELECTED, Constants.REJECTED);
            }

            @NonNull
            @Override
            public LiveData<ApiResponse<ProfileListResponse>> createCall() {
                return null;
            }
        }.getAsLiveData();
    }
}

package com.example.matchmakingdemo.ui.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.matchmakingdemo.data_source.store.models.Profile;
import com.example.matchmakingdemo.data_source.ProfileRepository;
import com.example.matchmakingdemo.util.Resource;

import java.util.List;

public class ProfileListViewModel extends AndroidViewModel {
    private static final String TAG = "ProfileListViewModel";

    private final ProfileRepository profileRepository;
    public static final String QUERY_EXHAUSTED = "No more results.";
    private boolean isQueryExhausted;
    private boolean isPerformingQuery;

    int total_count;

    private final MediatorLiveData<Resource<List<Profile>>> profiles = new MediatorLiveData<>();
    private final MediatorLiveData<Resource<List<Profile>>> actioned_profiles = new MediatorLiveData<>();

    public ProfileListViewModel(Application mApplication, int query) {
        super(mApplication);
        profileRepository = ProfileRepository.getInstance(getApplication());


        if (query == -1) {
            getSelectedDataFromCache();
        } else {
            getProfileListAPI(query);
        }
    }


    public LiveData<Resource<List<Profile>>> getProfiles() {
        return profiles;
    }

    public LiveData<Resource<List<Profile>>> getActionedProfiles() {
        return actioned_profiles;
    }

    public void getProfileListAPI(int query) {
        if (!isPerformingQuery) {
            isQueryExhausted = false;
            executeProfileListAPI(query);
        }
    }

    public void searchNextPage(int query) {
        if (!isQueryExhausted && !isPerformingQuery) {
            executeProfileListAPI(query);
        }
    }


    public void executeProfileListAPI(int query) {
        isPerformingQuery = true;

        //this is to make count of total requested queries, if its more than cached then only get data from network
        total_count = total_count + query;

        final LiveData<Resource<List<Profile>>> repoSource = profileRepository.getProfiles(query, total_count);
        profiles.addSource(repoSource, listResource -> {
            if (listResource != null) {
                if (listResource.status == Resource.Status.SUCCESS) {
                    Log.d(TAG, "onChanged: " + listResource.data);

                    isPerformingQuery = false;
                    if (listResource.data != null) {
                        if (listResource.data.size() == 0) {
                            Log.d(TAG, "onChanged: query is exhausted...");
                            profiles.setValue(
                                    new Resource<>(
                                            Resource.Status.ERROR,
                                            listResource.data,
                                            QUERY_EXHAUSTED
                                    )
                            );
                            isQueryExhausted = true;
                        }
                    }
                    profiles.removeSource(repoSource);
                } else if (listResource.status == Resource.Status.ERROR) {

                    isPerformingQuery = false;
                    assert listResource.message != null;
                    if (listResource.message.equals(QUERY_EXHAUSTED)) {
                        isQueryExhausted = true;
                    }
                    profiles.removeSource(repoSource);
                }

                profiles.setValue(listResource);
            } else {
                profiles.removeSource(repoSource);
            }
        });
    }


    public void getSelectedDataFromCache() {

        final LiveData<Resource<List<Profile>>> repoSource = profileRepository.getActionedProfiles();
        actioned_profiles.addSource(repoSource, listResource -> {
            if (listResource != null) {
                actioned_profiles.setValue(listResource);

                if (listResource.status == Resource.Status.SUCCESS || listResource.status == Resource.Status.ERROR)
                    actioned_profiles.removeSource(repoSource);
            } else {
                actioned_profiles.removeSource(repoSource);
            }
        });
    }


    public void updateUserSelection(long profile_id, String val) {
        profileRepository.updateUserSelectionStatus(profile_id, val);
    }

}

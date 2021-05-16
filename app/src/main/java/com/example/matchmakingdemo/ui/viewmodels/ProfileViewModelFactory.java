package com.example.matchmakingdemo.ui.viewmodels;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

public class ProfileViewModelFactory implements ViewModelProvider.Factory {

    private final Application mApplication;
    private final int query;

    public ProfileViewModelFactory(Application application, int query) {
        mApplication = application;
        this.query = query;
    }


    @NotNull
    @Override
    public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
        return (T) new ProfileListViewModel(mApplication, query);
    }
}

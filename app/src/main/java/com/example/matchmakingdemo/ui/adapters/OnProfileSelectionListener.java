package com.example.matchmakingdemo.ui.adapters;

import com.example.matchmakingdemo.data_source.store.models.Profile;

public interface OnProfileSelectionListener {
    void onSelectionClick(int position, boolean is_selected, Profile profile);
}

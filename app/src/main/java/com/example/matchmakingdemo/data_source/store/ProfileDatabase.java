package com.example.matchmakingdemo.data_source.store;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.matchmakingdemo.data_source.store.models.Profile;

@Database(entities = {Profile.class}, version = 1)
public abstract class ProfileDatabase extends RoomDatabase {
    private static final String TAG = "ProfileDatabase";

    public static final String DATABASE_NAME = "profiles_db";

    private static ProfileDatabase instance;

    public static ProfileDatabase getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    ProfileDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract ProfileDao getProfileDao();
}

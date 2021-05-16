package com.example.matchmakingdemo.data_source.store;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.matchmakingdemo.data_source.store.models.Profile;

import java.util.List;


import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ProfileDao {
    String TAG = "ProfileDao";

    @Insert(onConflict = IGNORE)
    long[] insertProfiles(Profile... Profiles);

    @Insert(onConflict = REPLACE)
    void insertProfile(Profile Profile);

    // Custom update n timestamp don't get removed
    @Query("UPDATE profiles SET gender = :gender,  title= :title, first = :first, " +
            "last = :last, city = :city, state = :state, country = :country,  age = :age " +
            "WHERE profile_id = :profile_id")
    void updateProfile(long profile_id, String gender, String title, String first, String last,
                       String city, String state, String country, int age);


    @Query("SELECT * FROM profiles WHERE user_selection_status = :user_selection_status")
    LiveData<List<Profile>> getAllProfiles(String user_selection_status);


    @Query("SELECT * FROM profiles WHERE user_selection_status = :user_selection_status_one " +
            "OR user_selection_status = :user_selection_status_two")
    LiveData<List<Profile>> getAllActionedProfiles(String user_selection_status_one,
                                                   String user_selection_status_two);

    @Query("UPDATE profiles SET user_selection_status = :user_selection_status WHERE" +
            " profile_id = :profile_id")
    int updateUserSelectionStatus(long profile_id, String user_selection_status);

}

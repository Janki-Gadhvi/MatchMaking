package com.example.matchmakingdemo.data_source.store.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.matchmakingdemo.util.Constants;

@Entity(tableName = "profiles")
public class Profile{
    private static final String TAG = "Profile";


    @PrimaryKey(autoGenerate = true)
    private long profile_id;

    String gender;
    private String title_status;

    @Embedded
    ProfileName name;
    @Embedded
    Location location;
    @Embedded
    DOB dob;
    @Embedded
    Picture picture;

    private int timestamp;

    private String user_selection_status = Constants.NO_ACTION;

    @Ignore
    public Profile() {
    }

    public Profile(long profile_id, String gender, String title_status, ProfileName name,
                   Location location, DOB dob, Picture picture, int timestamp, String user_selection_status) {
        this.profile_id = profile_id;
        this.gender = gender;
        this.title_status = title_status;
        this.name = name;
        this.location = location;
        this.dob = dob;
        this.picture = picture;
        this.timestamp = timestamp;
        this.user_selection_status = user_selection_status;
    }

    @Ignore
    public Profile(String gender, ProfileName name, Location location, DOB dob, Picture picture, int timestamp) {
        this.gender = gender;
        this.name = name;
        this.location = location;
        this.dob = dob;
        this.picture = picture;
        this.timestamp = timestamp;
    }

    public String getUser_selection_status() {
        return user_selection_status;
    }

    public void setUser_selection_status(String user_selection_status) {
        this.user_selection_status = user_selection_status;
    }

    public String getTitle_status() {
        return title_status;
    }

    public void setTitle_status(String title_status) {
        this.title_status = title_status;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public long getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(long profile_id) {
        this.profile_id = profile_id;
    }

    protected Profile(Parcel in) {
        gender = in.readString();
    }


    public static String getTAG() {
        return TAG;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ProfileName getName() {
        return name;
    }

    public void setName(ProfileName name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public DOB getDob() {
        return dob;
    }

    public void setDob(DOB dob) {
        this.dob = dob;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }


    @Override
    public String toString() {
        return "Profile{" +
                "profile_id=" + profile_id +
                ", gender='" + gender + '\'' +
                ", title_status='" + title_status + '\'' +
                ", name=" + name +
                ", location=" + location +
                ", dob=" + dob +
                ", picture=" + picture +
                ", timestamp=" + timestamp +
                ", user_selection_status='" + user_selection_status + '\'' +
                '}';
    }


}

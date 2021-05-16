package com.example.matchmakingdemo.data_source.store.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Location{
    private static final String TAG = "Location";

    String city, state, country;

    public Location(String city, String state, String country) {
        this.city = city;
        this.state = state;
        this.country = country;
    }

    protected Location(Parcel in) {
        city = in.readString();
        state = in.readString();
        country = in.readString();
    }

    public static String getTAG() {
        return TAG;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Location{" +
                "city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}

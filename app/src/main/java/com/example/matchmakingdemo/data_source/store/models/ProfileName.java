package com.example.matchmakingdemo.data_source.store.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ProfileName {
    private static final String TAG = "ProfileName";

    String title, first, last;

    public ProfileName(String title, String first, String last) {
        this.title = title;
        this.first = first;
        this.last = last;
    }

    public static String getTAG() {
        return TAG;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    @Override
    public String toString() {
        return "ProfileName{" +
                "title='" + title + '\'' +
                ", first='" + first + '\'' +
                ", last='" + last + '\'' +
                '}';
    }
}

package com.example.matchmakingdemo.data_source.store.models;

import android.os.Parcel;
import android.os.Parcelable;

public class DOB {
    private static final String TAG = "DOB";

    int age;

    public DOB(int age) {
        this.age = age;
    }

    protected DOB(Parcel in) {
        age = in.readInt();
    }

    public static String getTAG() {
        return TAG;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

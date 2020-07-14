package com.example.devhub.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Repositories implements Parcelable{

    @SerializedName("name")
    private String name;
    @SerializedName("full_name")
    private String fullName;

    protected Repositories(Parcel in) {
        name = in.readString();
        fullName = in.readString();
    }

    public static final Creator<Repositories> CREATOR = new Creator<Repositories>() {
        @Override
        public Repositories createFromParcel(Parcel in) {
            return new Repositories(in);
        }

        @Override
        public Repositories[] newArray(int size) {
            return new Repositories[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(fullName);
    }
}

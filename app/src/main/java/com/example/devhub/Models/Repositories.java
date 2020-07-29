package com.example.devhub.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Repositories implements Parcelable{

    @SerializedName("name")
    private String name;
    @SerializedName("full_name")
    private String fullName;
    @SerializedName("forks_count")
    private int forks;
    @SerializedName("stargazers_count")
    private int stars;
    @SerializedName("size")
    private int size;
    @SerializedName("description")
    private String description;
    @SerializedName("url")
    private String url;
    @SerializedName("language")
    private String language;


    protected Repositories(Parcel in) {
        name = in.readString();
        fullName = in.readString();
        forks = in.readInt();
        stars = in.readInt();
        size = in.readInt();
        description = in.readString();
        url = in.readString();
        language = in.readString();

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


    public int getForks() {
        return forks;
    }

    public int getStars() {
        return stars;
    }

    public String getDescription() {
        return description;
    }

    public String getLanguage() {
        return language;
    }

    public String getUrl() {
        return url;
    }

    public int getSizeOfRepo() {
        return size;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(fullName);
        parcel.writeInt(forks);
        parcel.writeInt(stars);
        parcel.writeInt(size);
        parcel.writeString(description);
        parcel.writeString(url);
        parcel.writeString(language);
    }
}

package com.evans.jobs.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Job implements Parcelable {

    @SerializedName("type")
    private String type;
    @SerializedName("url")
    private String jobUrl;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("company")
    private String company;
    @SerializedName("location")
    private String location;
    @SerializedName("title")
    private String title;
    @SerializedName("company_logo")
    private String logo;

    protected Job(Parcel in) {
        type = in.readString();
        jobUrl = in.readString();
        createdAt = in.readString();
        company = in.readString();
        location = in.readString();
        title = in.readString();
        logo = in.readString();
    }

    public static final Creator<Job> CREATOR = new Creator<Job>() {
        @Override
        public Job createFromParcel(Parcel in) {
            return new Job(in);
        }

        @Override
        public Job[] newArray(int size) {
            return new Job[size];
        }
    };

    public String getType() {
        return type;
    }

    public String getJobUrl() {
        return jobUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getCompany() {
        return company;
    }

    public String getLocation() {
        return location;
    }

    public String getTitle() {
        return title;
    }

    public String getLogo() {
        return logo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(type);
        parcel.writeString(jobUrl);
        parcel.writeString(createdAt);
        parcel.writeString(company);
        parcel.writeString(location);
        parcel.writeString(title);
        parcel.writeString(logo);
    }
}

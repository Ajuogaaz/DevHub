package com.example.devhub.Models;

import android.app.DownloadManager;
import android.os.Parcelable;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@ParseClassName("Followers")
public class Followers extends ParseObject implements Serializable {

    public static final String KEY_USER = "subjectUser";
    public static final String KEY_FOLLOWING_USER = "followingUser";


    public ParseUser getSubjectUser(){
        return getParseUser(KEY_USER);
    }
    public void setSubjectUser(ParseUser user){
        put (KEY_USER, user);
    }

    public ParseUser getFollowingUser(){
        return getParseUser(KEY_FOLLOWING_USER);
    }
    public void setFollowingUser(ParseUser user){
        put (KEY_FOLLOWING_USER, user);
    }


    public String getTime(){

        Date date = getCreatedAt();

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm");

        String currDate = format.format(date);

        return currDate;

    }

    public static void queryFollowers(  ParseUser user, FindCallback callback) {
        ParseQuery<Followers> query = ParseQuery.getQuery(Followers.class);
        query.include(Followers.KEY_USER);
        query.include(Followers.KEY_FOLLOWING_USER);
        if (user != null) {
            query.whereEqualTo(Followers.KEY_USER, user);
        }
        query.addDescendingOrder(Post.KEY_CREATED_AT);
        query.findInBackground(callback);

    }
    public static void queryFollowing(  ParseUser user, FindCallback callback) {
        ParseQuery<Followers> query = ParseQuery.getQuery(Followers.class);
        query.include(Followers.KEY_USER);
        query.include(Followers.KEY_FOLLOWING_USER);
        if (user != null) {
            query.whereEqualTo(Followers.KEY_FOLLOWING_USER, user);
        }
        query.addDescendingOrder(Post.KEY_CREATED_AT);
        query.findInBackground(callback);

    }
    public static void querytest( ParseUser user, ParseUser another,  FindCallback callback) {
        ParseQuery<Followers> query = ParseQuery.getQuery(Followers.class);
        query.include(Followers.KEY_USER);
        query.include(Followers.KEY_FOLLOWING_USER);
        if (user != null) {
            query.whereEqualTo(Followers.KEY_USER, user).whereEqualTo(Followers.KEY_FOLLOWING_USER, another);
        }
        query.addDescendingOrder(Post.KEY_CREATED_AT);
        query.findInBackground(callback);

    }

}

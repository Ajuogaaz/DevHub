package com.example.devhub.Models;

import android.app.DownloadManager;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.Date;

@ParseClassName("Followers")
public class Followers extends ParseObject {

    public static final String KEY_USER = "SubjectUser";
    public static final String KEY_FOLLOWING_USER = "followingUser";


    public String getSubjectUser(){
        return getString(KEY_USER);
    }
    public void setSubjectUser(String ObjectId){
        put (KEY_USER, ObjectId);
    }

    public ParseUser getFollowingUser(){
        return getParseUser(KEY_FOLLOWING_USER);
    }
    public void setFollowingUser(ParseUser user){
        put (KEY_FOLLOWING_USER, user);
    }


    public String getTime(){

        Date date = getCreatedAt();

        SimpleDateFormat format = new SimpleDateFormat("E MM dd yyyy hh:mm");

        String currDate = format.format(date);

        return currDate;

    }

    public static void queryFollowers(  ParseUser user, FindCallback callback) {
        ParseQuery<Followers> query = ParseQuery.getQuery(Followers.class);
        query.include(Followers.KEY_USER);
        query.include(Followers.KEY_FOLLOWING_USER);
        if (user != null) {
            query.whereEqualTo(Followers.KEY_USER, user.getObjectId());
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

}

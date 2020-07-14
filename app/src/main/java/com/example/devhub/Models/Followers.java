package com.example.devhub.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.Date;

@ParseClassName("Followers")
public class Followers extends ParseObject {

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

        SimpleDateFormat format = new SimpleDateFormat("E MM dd yyyy hh:mm");

        String currDate = format.format(date);

        return currDate;

    }




}

package com.example.devhub.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.Date;

@ParseClassName("Likes")
public class Likes extends ParseObject {

    public static final String KEY_USER = "likingUser";
    public static final String KEY_RELATED_POST = "relatedPost";


    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }
    public void setUser(ParseUser user){
        put (KEY_USER, user);
    }

    public Post getPost(){
        return (Post)getParseObject(KEY_RELATED_POST);
    }
    public void setPost(Post post){
        put (KEY_RELATED_POST, post);
    }


    public String getTime(){

        Date date = getCreatedAt();

        SimpleDateFormat format = new SimpleDateFormat("E MM dd yyyy hh:mm");

        String currDate = format.format(date);

        return currDate;

    }

}

package com.example.devhub.Models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.Date;

@ParseClassName("Comments")
public class Comments extends ParseObject {

    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_USER = "postingUser";
    public static final String KEY_CREATED_AT = "createdAt";
    public static final String KEY_POST = "relatedPost";

    public String getDescription(){
        return getString(KEY_DESCRIPTION);
    }
    public void setDescription(String description){
        put (KEY_DESCRIPTION, description);
    }

    public Post getPost(){
        return (Post)getParseObject(KEY_POST);
    }
    public void setPost(Post post){
        put (KEY_POST, post);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }
    public void setUser(ParseUser user){
        put (KEY_USER, user);
    }

    public String getTime(){

        Date date = getCreatedAt();

        SimpleDateFormat format = new SimpleDateFormat("hh:mm");

        String currDate = format.format(date);

        return currDate;

    }

}

package com.example.devhub.Models;


import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.Date;


//Adding the class tag
@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "postImage";
    public static final String KEY_USER = "postingUser";
    public static final String KEY_CREATED_AT = "createdAt";


    //Making getter and setter methods
    public String getDescription(){
        return getString(KEY_DESCRIPTION);
    }
    public void setDescription(String description){
        put (KEY_DESCRIPTION, description);
    }

    public ParseFile getImage(){
        return getParseFile(KEY_IMAGE);
    }
    public void setImage(ParseFile parseFile){
        put (KEY_IMAGE, parseFile);
    }

    public User getUser(){
        return (User) getParseUser(KEY_USER);
    }
    public void setUser(User user){
        put (KEY_USER, user);
    }


    public String getTime(){

        Date date = getCreatedAt();

        SimpleDateFormat format = new SimpleDateFormat("E MM dd yyyy hh:mm");

        String currDate = format.format(date);

        return currDate;

    }

    public static void query(int page, int limit, ParseUser currentUser, FindCallback callback) {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        if (currentUser != null) {
            query.whereEqualTo(Post.KEY_USER, currentUser);
        }
        query.setLimit(limit);
        query.setSkip(page * limit);
        query.addDescendingOrder(Post.KEY_CREATED_AT);
        query.findInBackground(callback);

    }
}

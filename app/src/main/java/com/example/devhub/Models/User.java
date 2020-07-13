package com.example.devhub.Models;


import com.parse.ParseClassName;
import com.parse.ParseUser;

@ParseClassName("User")
public class User extends ParseUser {

    public static final String KEY_NAME = "name";
    public static final String KEY_ = "postImage";
    public static final String KEY_USER = "postingUser";
    public static final String KEY_CREATED_AT = "createdAt";

}


package com.example.devhub.Models;


import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseUser;

@ParseClassName("User")
public class User extends ParseUser {

    public static final String KEY_NAME = "username";
    public static final String KEY_AUTH = "authData";
    public static final String KEY_PROFILE_PIC = "profilePic";



    public ParseFile getImage(){
        return getParseFile(KEY_PROFILE_PIC);
    }
    public void setImage(ParseFile parseFile){
        put (KEY_PROFILE_PIC, parseFile);
    }

    public String getAuth(){
        return getString(KEY_AUTH);
    }
    public void setAuth(String auth){
        put (KEY_AUTH, auth);
    }

}

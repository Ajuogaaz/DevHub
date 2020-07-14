
package com.example.devhub.Models;


import com.google.gson.annotations.SerializedName;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseUser;

@ParseClassName("User")
public class User extends ParseUser {

    //public static final String KEY_NAME = "username";
  /*  public static final String KEY_AUTH = "authData";
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
    }*/

    @SerializedName("login")
    private String username;
    @SerializedName("avatar_url")
    private String avatar;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("bio")
    private String bio;
    @SerializedName("public_repos")
    private int repos;

    public User(String username, String avatar, String name, String email, String bio, int repos) {
        this.username = username;
        this.avatar = avatar;
        this.name = name;
        this.email = email;
        this.bio = bio;
        this.repos = repos;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBio() {
        return bio;
    }

    public int getRepos() {
        return repos;
    }

}

package com.example.devhub.Models;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


@ParseClassName("MessageTop")
public class MessageTop extends ParseObject implements Serializable {

    public static final String KEY_BODY = "TopMessage";
    public static final String KEY_RECEIVING_USER = "UserOne";
    public static final String KEY_CREATED_AT = "createdAt";
    public static final String KEY_SENDING_USER = "UserTwo";

    public ParseUser getUserOne(){
        return getParseUser(KEY_RECEIVING_USER);
    }
    public void setUserOne(ParseUser user){
        put (KEY_RECEIVING_USER, user);
    }

    public ParseUser getUserTwo(){
        return getParseUser(KEY_SENDING_USER);
    }
    public void setUserTwo(ParseUser user){
        put (KEY_SENDING_USER, user);
    }
    public String getTopMessage(){
        return getString(KEY_BODY);
    }
    public void updateTopMessage(String body){
        put (KEY_BODY, body);
    }

    public String getTime(){

        Date date = getCreatedAt();

        SimpleDateFormat format = new SimpleDateFormat("E MM dd yyyy hh:mm");

        String currDate = format.format(date);

        return currDate;
    }

    public static void queryTopMessages(  ParseUser SendingUser, ParseUser ReceivingUser, FindCallback callback) {
        ParseQuery<Messages> query = ParseQuery.getQuery(Messages.class);
        query.include(Messages.KEY_RECEIVING_USER);
        query.include(Messages.KEY_SENDING_USER);

        query.whereEqualTo(KEY_RECEIVING_USER, ReceivingUser).whereEqualTo(KEY_SENDING_USER, SendingUser);
        query.whereEqualTo(KEY_RECEIVING_USER, SendingUser).whereEqualTo(KEY_SENDING_USER, ReceivingUser);

        query.addDescendingOrder(Post.KEY_CREATED_AT);
        query.findInBackground(callback);

    }



}

package com.example.devhub.Models;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@ParseClassName("MessageTop")
public class MessageTop extends ParseObject implements Serializable {

    public static final String KEY_BODY = "TopMessage";
    public static final String KEY_RECEIVING_USER = "User0ne";
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

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm");

        String currDate = format.format(date);

        return currDate;
    }

    public static void queryTopMessages( ParseUser currentUser, FindCallback callback) {
        List<ParseQuery<MessageTop>> query = new ArrayList<>();
        ParseQuery<MessageTop> query1 = ParseQuery.getQuery(MessageTop.class);
        ParseQuery<MessageTop> query2 = ParseQuery.getQuery(MessageTop.class);

        query1.whereEqualTo(KEY_RECEIVING_USER, currentUser);
        query2.whereEqualTo(KEY_SENDING_USER, currentUser);

        query.add(query1);
        query.add(query2);

        ParseQuery<MessageTop> mainquery = ParseQuery.or(query);

        mainquery.include(MessageTop.KEY_RECEIVING_USER);
        mainquery.include(MessageTop.KEY_SENDING_USER);

        mainquery.addDescendingOrder(MessageTop.KEY_CREATED_AT);
        mainquery.findInBackground(callback);

    }



}

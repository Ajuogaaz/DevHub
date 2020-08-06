package com.example.devhub.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.io.Serializable;


@ParseClassName("MessageTop")
public class MessageTop extends ParseObject implements Serializable {

    public static final String KEY_BODY = "TopMessage";
    public static final String KEY_RECEIVING_USER = "UserOne";
    public static final String KEY_CREATED_AT = "createdAt";
    public static final String KEY_SENDING_USER = "UserTwo";
}

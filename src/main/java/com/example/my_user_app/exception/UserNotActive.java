package com.example.my_user_app.exception;

public class UserNotActive extends RuntimeException{
    public UserNotActive(String message){
        super(message);
    }
}

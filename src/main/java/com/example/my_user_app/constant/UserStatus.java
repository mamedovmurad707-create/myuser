package com.example.my_user_app.constant;

public enum UserStatus {
    ACTIVE(1),
    BLOCKED(2),
    DELETE(3);

    private final int key;
    UserStatus(int key){
        this.key=key;
    }

    public int getKey(){
        return key;
    }
}

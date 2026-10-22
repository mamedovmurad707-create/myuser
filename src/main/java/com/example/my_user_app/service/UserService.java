package com.example.my_user_app.service;

import com.example.my_user_app.model.request.UserRequest;
import com.example.my_user_app.model.response.UserResponse;

public interface UserService {
    UserResponse create(UserRequest request);
    UserResponse getById(Long id);
    void updateByID(Long id, String name , Integer age );
    UserResponse deleteById(Long id);
}

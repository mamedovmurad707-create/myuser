package com.example.my_user_app.service;

import com.example.my_user_app.model.request.UserRequest;
import com.example.my_user_app.model.response.UserResponse;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserResponse create(UserRequest request);
    UserResponse getById(Long id);
    void updateById(Long id, UserRequest request );
    UserResponse deleteById(Long id);
   Page<UserResponse> getAllUsers(Pageable pageable);
}

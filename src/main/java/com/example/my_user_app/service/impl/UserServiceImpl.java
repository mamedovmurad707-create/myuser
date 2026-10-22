package com.example.my_user_app.service.impl;

import com.example.my_user_app.constant.UserStatus;
import com.example.my_user_app.entity.UserEntity;
import com.example.my_user_app.mapper.UserMapper;
import com.example.my_user_app.model.request.UserRequest;
import com.example.my_user_app.model.response.UserResponse;
import com.example.my_user_app.repository.UserRepository;
import com.example.my_user_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse create(UserRequest request) {
        UserEntity entity = userMapper.toEntity(request);
        UserEntity dbUser = userRepository.save(entity);
        return userMapper.toResponse(dbUser);
    }

    @Override
    public UserResponse getById(Long id) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found "));

        return userMapper.toResponse(entity);
    }

    @Override
    public void updateByID(Long id, String name, Integer age) {
       UserEntity entity = userRepository.findById(id).orElseThrow(()->
                new RuntimeException("user not found"));
       entity.setName(name);
       entity.setAge(age);
       userRepository.save(entity);

    }

    @Override
    public UserResponse deleteById(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(()->
                new RuntimeException("User by id:" + id + "not found"));
        user.setStatus(UserStatus.DELETE.getKey());
        userRepository.save(user);
        return userMapper.toResponse(user);
    }
}

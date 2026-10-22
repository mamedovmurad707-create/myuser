package com.example.my_user_app.service.impl;

import com.example.my_user_app.constant.UserStatus;
import com.example.my_user_app.entity.UserEntity;
import com.example.my_user_app.exception.UserNotFound;
import com.example.my_user_app.mapper.UserMapper;
import com.example.my_user_app.model.request.UserRequest;
import com.example.my_user_app.model.response.UserResponse;
import com.example.my_user_app.repository.UserRepository;
import com.example.my_user_app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse create(UserRequest request) {
        log.info("UserServiceImpl.create.start body:{}", request);
        UserEntity entity = userMapper.toEntity(request);
        UserEntity dbUser = userRepository.save(entity);
        log.info("UserServiceImpl.create.success body:{}", request);
        return userMapper.toResponse(dbUser);
    }

    @Override
    public UserResponse getById(Long id) {
        log.info("UserServiceImpl.getById.start id:{}", id);
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() ->
                        {
                            log.error("UserServiceImpl.getById.error id:{}", id);
                            return new UserNotFound("User not found ");
                        }
                );
        return userMapper.toResponse(entity);
    }

    @Override
    public void updateByID(Long id, String name, Integer age) {
        log.info("UserServiceImpl.updateByID.start id:{},name:{},age:{}",id,name,age);
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> {
                            log.error("UserServiceImpl.updateByID.error id:{}", id);
                            return new UserNotFound("user not found");
                        }
                );
        entity.setName(name);
        entity.setAge(age);
        userRepository.save(entity);
        log.info("UserServiceImpl.updateByID.success id:{},name:{},age:{}",id,name,age);

    }

    @Override
    public UserResponse deleteById(Long id) {
        log.info("UserServiceImpl.deleteById.start id:{}", id);
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> {
                            log.error("UserServiceImpl.deleteById.error. not found  id:{}", id);
                            return new UserNotFound("User by id:" + id + " not found");
                        }
                );
        user.setStatus(UserStatus.DELETE.getKey());
        userRepository.save(user);
        log.info("UserServiceImpl.deleteById.success id:{}", id);
        return userMapper.toResponse(user);
    }
}

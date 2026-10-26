package com.example.my_user_app.service.impl;

import static com.example.my_user_app.constant.UserStatus.DELETE;
import com.example.my_user_app.constant.UserStatus;
import com.example.my_user_app.entity.UserEntity;
import com.example.my_user_app.exception.UserNotFound;
import com.example.my_user_app.mapper.UserMapper;
import com.example.my_user_app.model.request.UserRequest;
import com.example.my_user_app.model.response.UserResponse;
import com.example.my_user_app.repository.UserRepository;
import com.example.my_user_app.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        log.info("UserServiceImpl.create.start");
        UserEntity entity = userMapper.toEntity(request);
        UserEntity dbUser = userRepository.save(entity);
        log.info("UserServiceImpl.create.success id:{}", dbUser.getId());
        return userMapper.toResponse(dbUser);
    }

    @Override
    public UserResponse getById(Long id) {
        log.info("UserServiceImpl.getById.start id:{}", id);
        UserEntity entity = findOrNotFount(id);
        return userMapper.toResponse(entity);
    }

    @Override
    @Transactional
    public void updateById(Long id, UserRequest request) {
        String name = request.getName();
        Integer age = request.getAge();
        Integer statusId = request.getStatusId();
        log.info("UserServiceImpl.updateByID.start id:{},name:{},age:{}",id,name,age);
        UserEntity entity = findOrNotFount(id);
        if (request.getName() != null) {
            entity.setName(request.getName());
        }

        if (request.getAge() != null) {
            entity.setAge(request.getAge());
        }
        log.info("UserServiceImpl.updateByID.success id:{},name:{},age:{}",id,name,age);

    }

    @Override
    public UserResponse deleteById(Long id) {
        log.info("UserServiceImpl.deleteById.start id:{}", id);
        UserEntity user = findOrNotFount(id);
        user.setStatus(DELETE.getKey());
        userRepository.save(user);
        log.info("UserServiceImpl.deleteById.success id:{}", id);
        return userMapper.toResponse(user);
    }

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAllActiveUsers(pageable, DELETE.getKey())
                .map(userMapper::toResponse);
    }

    private UserEntity findOrNotFount(Long id){
        return   userRepository.findByIdAndStatusNot(id, DELETE.getKey())
                .orElseThrow(() -> {
                    log.error("UserServiceImpl.error. not found  id:{}", id);
                    return new UserNotFound("User by id:" + id + " not found");
                });

    }
}

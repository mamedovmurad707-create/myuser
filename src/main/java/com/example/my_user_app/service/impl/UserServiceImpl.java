package com.example.my_user_app.service.impl;

import static com.example.my_user_app.constant.UserStatus.ACTIVE;
import static com.example.my_user_app.constant.UserStatus.DELETE;

import com.example.my_user_app.constant.UserStatus;
import com.example.my_user_app.entity.UserEntity;
import com.example.my_user_app.exception.InvalidCredentials;
import com.example.my_user_app.exception.UserAlreadyExist;
import com.example.my_user_app.exception.UserNotActive;
import com.example.my_user_app.exception.UserNotFound;
import com.example.my_user_app.mapper.UserMapper;
import com.example.my_user_app.model.request.UpdatePassRequest;
import com.example.my_user_app.model.request.UserRequest;
import com.example.my_user_app.model.request.VerifyCredentialsRequest;
import com.example.my_user_app.model.response.AuthUserResponse;
import com.example.my_user_app.model.response.UserResponse;
import com.example.my_user_app.repository.UserRepository;
import com.example.my_user_app.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse create(UserRequest request) {
         boolean exist = userRepository.existsByEmailAndStatusNot(request.getEmail(), DELETE.getKey());
         if (exist){
             throw new UserAlreadyExist("User already exist please choose another email: "+request.getEmail());
         }
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
        log.info("UserServiceImpl.updateByID.start id:{},name:{},age:{}", id, name, age);
        UserEntity entity = findOrNotFount(id);
        if (request.getName() != null) {
            entity.setName(request.getName());
        }

        if (request.getAge() != null) {
            entity.setAge(request.getAge());
        }
        log.info("UserServiceImpl.updateByID.success id:{},name:{},age:{}", id, name, age);

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

    @Override
    public UserResponse getUserByEmail(String email) {
        log.info("UserServiceImpl.getUserByEmail.start email: {}", email);

        var optUser = userRepository.findByEmailAndStatus(email, ACTIVE.getKey())
                .orElseThrow(() -> {
                    log.info("UserServiceImpl.getUserByEmail.error user not found email: {}", email);
                    return new UserNotFound("User with email not found: " + email);
                });
        log.info("UserServiceImpl.getUserByEmail.success ");
        return userMapper.toResponse(optUser);
    }

    @Override
    public String updateUserPassByEmail(String email, UpdatePassRequest request) {
      var optUser =  userRepository.findByEmailAndStatus(email, ACTIVE.getKey()).orElseThrow(()->
                {
                    log.info("UserServiceImpl.updateUserPassByEmail.error user not found email: {}", email);
                    return new UserNotFound("User with email not found: " + email);
                });
      if (passwordEncoder.matches(request.getOldPassword(), optUser.getPassword())) {
          optUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
      }else{
          throw new InvalidCredentials("Wrong old Password by user email: "+ email);
      }
        userRepository.save(optUser);
        return "User password update success email: "+email;
    }

    @Override
    public AuthUserResponse verifyCredentials(VerifyCredentialsRequest request) {
       var userDb = userRepository.findByEmailAndStatusNot(request.email(), DELETE.getKey())
                .filter(u-> passwordEncoder.matches(request.password(),u.getPassword()))
                .orElseThrow(()->
                        new InvalidCredentials("Email or password is incorrect"));
       if (userDb.getStatus() != ACTIVE.getKey()){
           throw new UserNotActive("User is not active");
       }

        return new AuthUserResponse(userDb.getId(),userDb.getEmail(),userDb.getRole(), userDb.getStatus());
    }

    private UserEntity findOrNotFount(Long id) {
        return userRepository.findByIdAndStatusNot(id, DELETE.getKey())
                .orElseThrow(() -> {
                    log.error("UserServiceImpl.error. not found  id:{}", id);
                    return new UserNotFound("User by id:" + id + " not found");
                });

    }
}

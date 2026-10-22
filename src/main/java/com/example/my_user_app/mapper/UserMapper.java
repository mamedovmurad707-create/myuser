package com.example.my_user_app.mapper;

import com.example.my_user_app.entity.UserEntity;
import com.example.my_user_app.model.request.UserRequest;
import com.example.my_user_app.model.response.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public  UserResponse toResponse(UserEntity entity){

        return UserResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .age(entity.getAge())
                .statusId(entity.getStatus())
                .build();
    }
    public  UserEntity toEntity(UserRequest request){
        UserEntity userEntity =new UserEntity();
        userEntity.setName(request.getName());
        userEntity.setEmail(request.getEmail());
        userEntity.setAge(request.getAge());
        userEntity.setPhone(String.valueOf(request.getPhoneNumber()));
return userEntity;
    }
}

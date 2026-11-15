package com.example.my_user_app.model.response;

import com.example.my_user_app.constant.Role;

public record AuthUserResponse(Long id , String email, Role role , Integer statusId) {
}

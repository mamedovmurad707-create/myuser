package com.example.my_user_app.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record VerifyCredentialsRequest(@NotBlank @Email String email,
                                       @NotBlank String password) {
}

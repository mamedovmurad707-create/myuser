package com.example.my_user_app.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePassRequest {
    @NotBlank
    private String oldPassword;
    @NotBlank
    @Size(min = 6, max = 64)
    private String newPassword;
}

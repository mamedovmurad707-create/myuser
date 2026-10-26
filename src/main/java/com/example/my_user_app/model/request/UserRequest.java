package com.example.my_user_app.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    @Size(min = 3,max = 32, message = "minimum 3 simbol max 32")
    private String name;
    @Email(message = "Email format is invalid")
    private String email;
    private Long phoneNumber;
    @Min(14)
    private Integer age;
    private Integer statusId;
}

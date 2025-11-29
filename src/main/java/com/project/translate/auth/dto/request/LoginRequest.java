package com.project.translate.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email format is not valid")
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;

}

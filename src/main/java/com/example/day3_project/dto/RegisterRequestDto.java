package com.example.day3_project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisterRequestDto {
    @Email(message="email should not be blank")
    @NotBlank(message="email is required")
    private String email;

    @NotBlank(message="password must be required")
    @Min(value=6,message="password must be atleast 6 characters")
    private String password;
}

package com.example.day3_project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentRequestDto {
    @NotBlank(message = "name should not be blank")
    private String name;

    @Min(value=5, message= "Age cannot be less than 5")
    @Max(value=90, message= "Age cannot be more than 90")
    private int age;

    @Email(message="Email should be valid")
    @NotBlank(message="Email cannot be blank")
    private String email;
}

package com.example.day3_project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentPatchRequestDto {
    private String name;

    @Min(value=5, message= "Age cannot be less than 5")
    @Max(value=90, message= "Age cannot be more than 90")
    private Integer age;

    @Email(message="Email should be valid")
    private String email;
}

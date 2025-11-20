package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserRequestDto {

    @NotBlank(message = "Name is Required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Incorrect Email")
    private String email;

    public UserRequestDto() {}
    public UserRequestDto(String name, String email) {
        this.name = name;
        this.email = email;
    }
}

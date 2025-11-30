package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserRequestDto {

    @NotBlank(message = "Name is Required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Incorrect Email")
    private String email;


    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "Password must be 6 and 100 characters")
    private String password;

    public UserRequestDto() {}
    public UserRequestDto(String name, String email,  String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}

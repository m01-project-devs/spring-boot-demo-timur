package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

public class UserResponseDto
{
    @Getter
    @Setter
    Long id;
    @Getter
    @Setter
    String name;
    @Getter
    @Setter
    String email;

    public UserResponseDto(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

}

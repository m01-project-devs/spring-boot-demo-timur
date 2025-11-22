package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserResponseDto
{
    Long id;
    String name;
    String email;

    public UserResponseDto(Long id, String name, String email)
    {
        this.id = id;
        this.name = name;
        this.email = email;
    }

}

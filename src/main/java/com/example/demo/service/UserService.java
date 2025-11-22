package com.example.demo.service;

import com.example.demo.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Optional;


public interface UserService
{
    User createUser(User user);
    Optional<User> findByEmail(String email);
    List<User> getAllUsers();
    User updateUser(User user);
    void deleteUser(String email);
}

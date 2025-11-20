package com.example.demo.controller;


import com.example.demo.dto.UserRequestDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        User created = userService.createUser(user);
        return ResponseEntity
                .created(URI.create("/api/users/email/" + created.getEmail()))
                .body(new UserResponseDto(created.getId(), created.getName(), created.getEmail()));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(
                userService.getAllUsers().stream()
                        .map(u -> new UserResponseDto(u.getId(), u.getName(), u.getEmail()))
                        .toList()
        );
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDto> getUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email)
                .map(u -> ResponseEntity.ok(new UserResponseDto(u.getId(), u.getName(), u.getEmail())))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/email/{email}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable String email,
            @Valid @RequestBody UserRequestDto dto) {

        User user = new User();
        user.setEmail(email);  // ищем по старому email
        user.setName(dto.getName());
        // user.setEmail(dto.getEmail()); // если хочешь разрешить смену email

        User updated = userService.updateUser(user);
        return ResponseEntity.ok(new UserResponseDto(updated.getId(), updated.getName(), updated.getEmail()));
    }

    @DeleteMapping("/email/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
        return ResponseEntity.noContent().build();
    }
}

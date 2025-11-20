package com.example.demo;



import com.example.demo.controller.UserController;
import com.example.demo.dto.UserRequestDto;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    private User createUser(Long id, String name, String email) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        return user;
    }

    @Test
    void createUser_validRequest_returns201AndLocation() throws Exception {
        UserRequestDto requestDto = new UserRequestDto("Ahmet", "ahmet@test.com");
        User savedUser = createUser(1L, "Ahmet", "ahmet@test.com");

        when(userService.createUser(any(User.class))).thenReturn(savedUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/users/email/ahmet@test.com"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Ahmet"))
                .andExpect(jsonPath("$.email").value("ahmet@test.com"));
    }

    @Test
    void createUser_invalidEmail_returns400() throws Exception {
        UserRequestDto invalid = new UserRequestDto("Ahmet", "wrong-email");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getUserByEmail_existing_returns200() throws Exception {
        User user = createUser(1L, "Veli", "veli@test.com");
        when(userService.findByEmail("veli@test.com")).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/email/veli@test.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Veli"))
                .andExpect(jsonPath("$.email").value("veli@test.com"));
    }

    @Test
    void getUserByEmail_notFound_returns404() throws Exception {
        when(userService.findByEmail("unknown@test.com")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/email/unknown@test.com"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUserByEmail_validData_returns200() throws Exception {
        UserRequestDto dto = new UserRequestDto("Mehmet", "mehmet.new@test.com");
        User updatedUser = createUser(1L, "Mehmet", "mehmet.new@test.com");

        when(userService.updateUser(any(User.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/api/users/email/mehmet@test.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mehmet"))
                .andExpect(jsonPath("$.email").value("mehmet.new@test.com"));
    }

    @Test
    void deleteUserByEmail_returns204() throws Exception {
        doNothing().when(userService).deleteUser("ali@test.com");

        mockMvc.perform(delete("/api/users/email/ali@test.com"))
                .andExpect(status().isNoContent());

        verify(userService).deleteUser("ali@test.com");
    }

    @Test
    void getAllUsers_returnsList() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(
                createUser(1L, "Ali", "ali@test.com"),
                createUser(2L, "Ayse", "ayse@test.com")
        ));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Ali"));
    }
}

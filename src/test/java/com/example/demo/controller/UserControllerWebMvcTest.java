package com.example.demo.controller;

import com.example.demo.generated.dto.UserDto;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(TestSecurityConfiguration.class)
class UserControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void createUser_withMissingName_shouldReturnBadRequest() throws Exception {
        // Given: UserDto without name
        UserDto userDto = new UserDto();

        String requestBody = objectMapper.writeValueAsString(userDto);

        // When & Then: POST request should return 400 Bad Request due to validation
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createUser_withEmptyName_shouldReturnBadRequest() throws Exception {
        // Given: UserDto with empty string name
        UserDto userDto = new UserDto();
        userDto.setName("");

        String requestBody = objectMapper.writeValueAsString(userDto);

        // When & Then: POST request should return 400 Bad Request due to validation
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createUser_withBlankName_shouldReturnBadRequest() throws Exception {
        // Given: UserDto with whitespace-only name
        UserDto userDto = new UserDto();
        userDto.setName("   ");

        String requestBody = objectMapper.writeValueAsString(userDto);

        // When & Then: POST request should return 400 Bad Request due to validation
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createUser_withValidName_shouldReturnCreated() throws Exception {
        // Given: Valid UserDto
        UserDto userDto = new UserDto();
        userDto.setName("Test User");

        UserDto savedUser = new UserDto();
        savedUser.setId(1L);
        savedUser.setName("Test User");

        when(userService.save(any(UserDto.class))).thenReturn(savedUser);

        String requestBody = objectMapper.writeValueAsString(userDto);

        // When & Then: POST request should return 201 Created
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser(roles = "USER")
    void createUser_asNonAdmin_shouldReturnForbidden() throws Exception {
        // Given: Valid UserDto but non-admin user
        UserDto userDto = new UserDto();
        userDto.setName("Test User");

        String requestBody = objectMapper.writeValueAsString(userDto);

        // When & Then: POST request should return 403 Forbidden
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isForbidden());
    }

    @Test
    void createUser_withoutAuthentication_shouldReturnUnauthorized() throws Exception {
        // Given: Valid UserDto but no authentication
        UserDto userDto = new UserDto();
        userDto.setName("Test User");

        String requestBody = objectMapper.writeValueAsString(userDto);

        // When & Then: POST request should return 401 Unauthorized
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isUnauthorized());
    }
}
package cl.cediaz.desafiospringboot.controller;

import cl.cediaz.desafiospringboot.dto.UserRequest;
import cl.cediaz.desafiospringboot.dto.UserResponse;
import cl.cediaz.desafiospringboot.dto.UserUpdateRequest;
import cl.cediaz.desafiospringboot.entity.User;
import cl.cediaz.desafiospringboot.mapper.UserMapper;
import cl.cediaz.desafiospringboot.security.CustomUserDetailsService;
import cl.cediaz.desafiospringboot.service.UserService;
import cl.cediaz.desafiospringboot.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void testGetUserById() throws Exception {
        User user = User.builder().id(1L).username("testuser").build();
        UserResponse response = new UserResponse(1L, "testuser", "ROLE_USER");

        when(userService.findById(anyLong())).thenReturn(user);
        when(userMapper.toResponse(user)).thenReturn(response);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.role").value("ROLE_USER"));
    }

    @Test
    void testCreateUser() throws Exception {
        User user = User.builder().id(1L).username("newuser").build();
        UserResponse response = new UserResponse(1L, "newuser", "ROLE_USER");

        when(userService.createUser(any(UserRequest.class))).thenReturn(user);
        when(userMapper.toResponse(user)).thenReturn(response);

        String requestBody = """
                {
                  "username": "newuser",
                  "password": "secret",
                  "role": "ROLE_USER"
                }
                """;

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("newuser"))
                .andExpect(jsonPath("$.role").value("ROLE_USER"));
    }

    @Test
    void testUpdateUser() throws Exception {
        User user = User.builder().id(1L).username("updateduser").build();
        UserResponse response = new UserResponse(1L, "updateduser", "ROLE_ADMIN");

        when(userService.updateUser(anyLong(), any(UserUpdateRequest.class))).thenReturn(user);
        when(userMapper.toResponse(user)).thenReturn(response);

        String requestBody = """
                {
                  "username": "updateduser",
                  "password": "newpass",
                  "roleId": 1
                }
                """;

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("updateduser"))
                .andExpect(jsonPath("$.role").value("ROLE_ADMIN"));
    }

    @Test
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }
}

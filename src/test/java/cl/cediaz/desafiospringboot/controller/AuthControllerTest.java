package cl.cediaz.desafiospringboot.controller;

import cl.cediaz.desafiospringboot.dto.UserRequest;
import cl.cediaz.desafiospringboot.dto.UserResponse;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void testLoginSuccess() throws Exception {
        Authentication auth = new UsernamePasswordAuthenticationToken("testuser", "secret");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
        when(jwtUtil.generateToken(anyString())).thenReturn("fake-jwt-token");
        when(userService.findByUsername(anyString())).thenReturn(User.builder().id(1L).username("testuser").build());

        String requestBody = """
                {
                  "username": "testuser",
                  "password": "secret"
                }
                """;

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("fake-jwt-token"));
    }

    @Test
    void testRegisterSuccess() throws Exception {
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

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("newuser"))
                .andExpect(jsonPath("$.role").value("ROLE_USER"));
    }
}

package com.previred.spa.controller;

import com.previred.spa.dto.AuthenticationRequestDTO;
import com.previred.spa.dto.UserDTO;
import com.previred.spa.dto.UserRequestDTO;
import com.previred.spa.entity.User;
import com.previred.spa.util.JwtUtil;
import com.previred.spa.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Endpoint para registrar un nuevo usuario.
     *
     * @param userRequest DTO que contiene los datos del usuario para el registro (username, password, role).
     * @return DTO del usuario registrado, que incluye su ID, nombre de usuario y rol.
     */
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserRequestDTO userRequest) {
        User createdUser = userService.createUser(userRequest);
        UserDTO userDTO = modelMapper.map(createdUser, UserDTO.class);
        return ResponseEntity.ok(userDTO);
    }

    /**
     * Endpoint para autenticar un usuario y generar un token JWT.
     *
     * @param authRequest DTO que contiene las credenciales del usuario (username y password).
     * @return Token JWT si las credenciales son válidas.
     */
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequestDTO authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        String jwt = jwtUtil.generateToken(authRequest.getUsername());
        return ResponseEntity.ok(jwt);
    }
}


package com.gestion.tarea.controller;

import com.gestion.tarea.dto.UsuarioDto;
import com.gestion.tarea.repository.UsuariosRepository;
import com.gestion.tarea.request.LoginRequest;
import com.gestion.tarea.request.SignupRequest;
import com.gestion.tarea.response.UserInfoResponse;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.tarea.security.jwt.JwtUtils;
import com.gestion.tarea.security.services.UserDetailsImpl;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UsuariosRepository userRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

    Authentication authentication = authenticationManager.authenticate(authRequest);
    SecurityContext securityContext = SecurityContextHolder.getContext();
    securityContext.setAuthentication(authentication);

    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
    List<String> roles = new ArrayList<String>();
    roles.add(userDetails.getRol());
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
        .body(new UserInfoResponse(userDetails.getId(),
                    userDetails.getUsername(),
                    roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUserName(signUpRequest.getUsername())) {
      return new ResponseEntity("Error: Username ya esta en uso", HttpStatus.BAD_REQUEST);
    }

    UsuarioDto usuarioDto = new UsuarioDto();
    usuarioDto.setHabilitado(true);
    usuarioDto.setUserName(signUpRequest.getUsername());
    usuarioDto.setPass(encoder.encode(signUpRequest.getPassword()));
    usuarioDto.setRol(signUpRequest.getRole());

    userRepository.save(usuarioDto);

    return new ResponseEntity("Usuario registrado exitosamente",HttpStatus.OK);
  }

  @PostMapping("/signout")
  public ResponseEntity<?> logoutUser() {
    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body("Sesion cerrada");
  }
}

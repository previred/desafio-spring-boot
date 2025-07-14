package com.previred.task_manager.service.impl;

import com.previred.task_manager.components.exceptions.GeneralException;
import com.previred.task_manager.components.jwt.JwtTokenService;
import com.previred.task_manager.dto.user.token.LoginReqDto;
import com.previred.task_manager.dto.user.token.LoginRespDto;
import com.previred.task_manager.service.IAuthService;
import com.previred.task_manager.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements IAuthService {
    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    public AuthServiceImpl(IUserService userService, PasswordEncoder passwordEncoder, JwtTokenService jwtTokenService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public LoginRespDto login(LoginReqDto loginRequest) {
        log.info("AuthServiceImpl.login INIT {}",loginRequest.email());

        var userFound = userService.findByEmail(loginRequest.email());

        if (!passwordEncoder.matches(loginRequest.password(), userFound.getPassword())) {
            throw new GeneralException("Credenciales inválidas", HttpStatus.UNAUTHORIZED);
        }
        userFound.setLastLogin(LocalDateTime.now());

        String token = jwtTokenService.generateToken(userFound.getId());

        userService.updateUserTokenByUserId(userFound.getId(), token);
        log.info("AuthServiceImpl.login END {}",loginRequest.email());
        return new LoginRespDto(token);
    }
}

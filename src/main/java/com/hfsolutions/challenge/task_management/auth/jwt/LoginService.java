package com.hfsolutions.challenge.task_management.auth.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.hfsolutions.challenge.task_management.auth.jwt.repository.UserEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public String login(UserEntity userEntity) throws Exception {
        try {
            UsernamePasswordAuthenticationToken upAuth = new UsernamePasswordAuthenticationToken(userEntity.getEmail(),
                    userEntity.getPassword());
            authenticationManager.authenticate(upAuth);
            String token = jwtTokenProvider.generateToken(userEntity.getEmail());
            return token;
        } catch (Exception e) {
            logger.error("login(UserEntity): " + e.getMessage());
            e.printStackTrace();
            throw new Exception("Login failed: " + e.getMessage());
        }
    }

}

package com.hfsolutions.challenge.task_management.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hfsolutions.challenge.task_management.auth.jwt.LoginService;
import com.hfsolutions.challenge.task_management.auth.jwt.RegisterService;
import com.hfsolutions.challenge.task_management.auth.jwt.repository.UserMapper;
import com.hfsolutions.challenge.task_management.openapi.AuthenticationApiDelegate;
import com.hfsolutions.challenge.task_management.openapi.model.Login200Response;
import com.hfsolutions.challenge.task_management.openapi.model.Message;
import com.hfsolutions.challenge.task_management.openapi.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationApiDelegateImpl implements AuthenticationApiDelegate {

    private final LoginService loginService;
    private final RegisterService registerService;
    private final UserMapper userMapper;

    @Override
    public ResponseEntity<Login200Response> login(User user) {
        try {
            String token = loginService.login(userMapper.toEntity(user));
            return new ResponseEntity<Login200Response>(new Login200Response(token), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<Message> register(User user) {
        try {
            registerService.register(userMapper.toEntity(user));
            return new ResponseEntity<Message>(
                    new Message("User created successfully", "The user " + user.getEmail() + " is created"),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<Message>(
                    new Message("Oops, failed to register user", e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

}

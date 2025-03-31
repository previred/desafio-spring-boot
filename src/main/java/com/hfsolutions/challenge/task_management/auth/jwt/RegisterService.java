package com.hfsolutions.challenge.task_management.auth.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hfsolutions.challenge.task_management.auth.jwt.repository.UserEntity;
import com.hfsolutions.challenge.task_management.auth.jwt.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private static final Logger logger = LoggerFactory.getLogger(RegisterService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserEntity register(UserEntity userEntity) {
        try {

            if (userRepository.existsByEmail(userEntity.getEmail())) {
                throw new Exception("The email provided '" + userEntity.getEmail() + "' is already registered");
            }

            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            userRepository.save(userEntity);
            return userEntity;

        } catch (Exception e) {
            logger.error("register(UserEntity): " + e.getMessage());
            throw new RuntimeException("An error occurred while trying to create the user: " + e.getMessage());
        }
    }

}

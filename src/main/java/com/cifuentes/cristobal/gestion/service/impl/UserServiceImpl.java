package com.cifuentes.cristobal.gestion.service.impl;

import org.springframework.stereotype.Service;

import com.cifuentes.cristobal.gestion.model.entity.User;
import com.cifuentes.cristobal.gestion.repository.UserRepository;
import com.cifuentes.cristobal.gestion.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    
    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
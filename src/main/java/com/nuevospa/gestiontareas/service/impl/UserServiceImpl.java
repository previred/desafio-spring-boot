package com.nuevospa.gestiontareas.service.impl;

import com.nuevospa.gestiontareas.model.User;
import com.nuevospa.gestiontareas.repository.UserRepository;
import com.nuevospa.gestiontareas.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository usuarioRepository;

    public UserServiceImpl(UserRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findById(Long id) {
        return usuarioRepository.findById(id);
    }
}
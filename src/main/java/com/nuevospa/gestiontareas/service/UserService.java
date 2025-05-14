package com.nuevospa.gestiontareas.service;

import com.nuevospa.gestiontareas.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);

    Optional<User> findById(Long id);
}
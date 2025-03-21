package com.previred.spa.service.impl;

import com.previred.spa.dto.UserRequestDTO;
import com.previred.spa.entity.User;
import com.previred.spa.repository.UserRepository;
import com.previred.spa.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Crea un nuevo usuario en el sistema.
     *
     * @param userRequest DTO que contiene los datos del usuario a registrar.
     * @return Entidad `User` del usuario creado.
     */
    @Override
    public User createUser(UserRequestDTO userRequest) {
        User user = modelMapper.map(userRequest, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}

package com.previred.task_manager.service.impl;


import com.previred.task_manager.components.exceptions.GeneralException;
import com.previred.task_manager.domain.repositories.IUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final IUserRepository userRepository;

    public CustomUserDetailsService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        var userFound = userRepository.findById(validUUId(userId))
                .orElseThrow(() ->  new GeneralException("User no existe", HttpStatus.NOT_FOUND));

        var authorities = Collections.singleton(new SimpleGrantedAuthority("USER"));
        return new org.springframework.security.core.userdetails.User(userFound.getEmail(),
userFound.getPassword(),
                userFound.getIsActive(), true, true, true,
                authorities
                );
    }

    private static UUID validUUId(String userId) {
        try {
            return  UUID.fromString(userId);
        } catch (IllegalArgumentException ex) {
            throw new UsernameNotFoundException("El id del usuario no es válido: " + userId);
        }
    }
}

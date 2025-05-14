package com.nuevospa.gestiontareas.service.impl;

import com.nuevospa.gestiontareas.model.User;
import com.nuevospa.gestiontareas.repository.UserRepository;
import com.nuevospa.gestiontareas.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void findByUsername_existingUser_returnsUser() {
        User user = new User();
        user.setUsername("test");

        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByUsername("test");
        assertTrue(result.isPresent());
        assertEquals("test", result.get().getUsername());
    }

    @Test
    void findByUsername_notFound_returnsEmpty() {
        when(userRepository.findByUsername("notfound")).thenReturn(Optional.empty());

        Optional<User> result = userService.findByUsername("notfound");
        assertFalse(result.isPresent());
    }

    @Test
    void findById_existingId_returnsUser() {
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findById(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void findById_notFound_returnsEmpty() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<User> result = userService.findById(99L);
        assertFalse(result.isPresent());
    }
}
package com.hfsolutions.challenge.task_management.auth.jwt.repository;

import org.springframework.stereotype.Component;

import com.hfsolutions.challenge.task_management.openapi.model.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public UserEntity toEntity(User userDto) {
        UserEntity entity = new UserEntity();
        entity.setEmail(userDto.getEmail());
        entity.setPassword(userDto.getPassword());
        return entity;
    }

    public User toDto(UserEntity entity) {
        User userDto = new User();
        userDto.setEmail(entity.getEmail());
        userDto.setPassword(entity.getPassword());
        return userDto;
    }
}

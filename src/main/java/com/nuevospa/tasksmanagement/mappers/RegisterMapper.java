package com.nuevospa.tasksmanagement.mappers;

import com.nuevospa.tasksmanagement.entities.User;
import com.nuevospa.tasksmanagement.model.RegisterResponse;

import java.time.ZoneOffset;

public class RegisterMapper {

    /**
     * Converts a User entity to a RegisterResponse DTO.
     * This method handles the mapping logic and excludes sensitive information like password.
     * @param user The User entity to convert.
     * @return UserResponse DTO.
     */
    public static RegisterResponse convertToDto(User user) {
        return new RegisterResponse(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getDateCreation().atOffset(ZoneOffset.UTC)
        );
    }
}

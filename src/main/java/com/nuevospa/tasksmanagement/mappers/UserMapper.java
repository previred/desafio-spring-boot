package com.nuevospa.tasksmanagement.mappers;

import com.nuevospa.tasksmanagement.entities.User;
import com.nuevospa.tasksmanagement.model.UserResponse;

import java.time.ZoneOffset;

public class UserMapper {

    /**
     * Converts a User entity to a UserResponse DTO.
     * This method handles the mapping logic and excludes sensitive information like password.
     * @param user The User entity to convert.
     * @return UserResponse DTO.
     */
    public static UserResponse convertToDto(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getDateCreation().atOffset(ZoneOffset.UTC)
        );
    }
}

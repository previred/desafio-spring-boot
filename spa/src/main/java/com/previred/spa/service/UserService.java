package com.previred.spa.service;

import com.previred.spa.dto.UserRequestDTO;
import com.previred.spa.entity.User;

public interface UserService {
    User createUser(UserRequestDTO userRequest);
}

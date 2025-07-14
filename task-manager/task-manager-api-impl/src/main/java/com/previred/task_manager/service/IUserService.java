package com.previred.task_manager.service;


import com.previred.task_manager.domain.model.User;
import com.previred.task_manager.dto.user.UserGetRespDto;
import com.previred.task_manager.dto.user.UserReqDto;
import com.previred.task_manager.dto.user.UserRespDto;

import java.util.UUID;

public interface IUserService {
    UserRespDto saveUser(UserReqDto reqDto);
    UserGetRespDto getUserById(UUID id);
    User findByEmail(String email);
    void updateUserTokenByUserId(UUID userId, String newToken);
}

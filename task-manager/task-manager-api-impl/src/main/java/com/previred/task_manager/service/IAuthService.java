package com.previred.task_manager.service;


import com.previred.task_manager.dto.user.token.LoginReqDto;
import com.previred.task_manager.dto.user.token.LoginRespDto;

public interface IAuthService {
    LoginRespDto login(LoginReqDto loginRequest);
}

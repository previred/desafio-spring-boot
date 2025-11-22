package com.yoserp1.tareas.service;

import com.yoserp1.tareas.dto.JwtResponseDTO;
import com.yoserp1.tareas.dto.LoginRequestDTO;

public interface IAuthService {
  JwtResponseDTO login(LoginRequestDTO request);

  LoginRequestDTO getPerfil(String username);
}

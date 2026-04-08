package io.github.srdejo.task_manager.service;

import io.github.srdejo.task_manager.model.AuthRequest;
import io.github.srdejo.task_manager.model.AuthResponse;

public interface AuthService {
    AuthResponse login(AuthRequest request);
}

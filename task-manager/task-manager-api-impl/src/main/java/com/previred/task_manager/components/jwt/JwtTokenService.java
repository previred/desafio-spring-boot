package com.previred.task_manager.components.jwt;

import java.util.UUID;

public interface JwtTokenService {
    String generateToken(UUID userId);
    boolean validateToken(String token);
    String getUserIdFromJWT(String token);
}

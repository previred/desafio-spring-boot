package cl.previred.desafio.taskmanager.infrastructure.security;

import cl.previred.desafio.taskmanager.infrastructure.persistence.jpa.entities.UserJpaEntity;
import cl.previred.desafio.taskmanager.infrastructure.persistence.jpa.repositories.UserJpaRepository;
import cl.previred.desafio.taskmanager.infrastructure.security.jwt.JwtService;
import cl.previred.desafio.taskmanager.infrastructure.web.dto.AuthRequest;
import cl.previred.desafio.taskmanager.infrastructure.web.dto.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserJpaRepository userRepository;
    private final JwtService jwtService;

    public AuthResponse authenticate(AuthRequest request) {

        UserJpaEntity user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + request.username()));

        String jwtToken = jwtService.generateToken(user);

        return new AuthResponse(jwtToken);
    }
}

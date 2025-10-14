package gestion.tareas.demo.infrastructure.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import gestion.tareas.demo.application.get.usuario.GetUsuarioGateway;
import gestion.tareas.demo.controller.AuthApi;
import gestion.tareas.demo.db.JwtResponseDto;
import gestion.tareas.demo.db.LoginRequestDto;
import gestion.tareas.demo.infrastructure.db.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {
    private final GetUsuarioGateway getUsuarioGateway;

    @Override
    public ResponseEntity<JwtResponseDto> authLoginPost(@RequestBody LoginRequestDto loginRequestDto) {
        Usuario usuario = getUsuarioGateway.findByUsername(loginRequestDto.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        JwtResponseDto jwtResponseDto = new JwtResponseDto();
        if (loginRequestDto.getPassword().equals(usuario.getPassword())) {
            String token = JWT.create()
                    .withSubject(usuario.getUsername())
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 1000))
                    .sign(Algorithm.HMAC256("secreto"));

            jwtResponseDto.setToken(token);
        }
        return ResponseEntity.ok(jwtResponseDto);
    }
}

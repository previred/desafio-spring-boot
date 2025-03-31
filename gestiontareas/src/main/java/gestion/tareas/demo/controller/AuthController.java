package gestion.tareas.demo.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import gestion.tareas.demo.model.JwtResponseDto;
import gestion.tareas.demo.model.LoginRequestDto;
import gestion.tareas.demo.model.Usuario;
import gestion.tareas.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class AuthController implements AuthApi{

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public ResponseEntity<JwtResponseDto> authLoginPost(@RequestBody LoginRequestDto loginRequestDto) {
        Usuario usuario = usuarioService.findByUsername(loginRequestDto.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        JwtResponseDto jwtResponseDto = new JwtResponseDto();
        if (loginRequestDto.getPassword().equals(usuario.getPassword())) {
            String token = JWT.create()
                    .withSubject(usuario.getUsername())
                    .withIssuedAt(new Date())  // Fecha de emisión
                    .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 1000))
                    .sign(Algorithm.HMAC256("secreto"));

            jwtResponseDto.setToken(token);
        }
        return ResponseEntity.ok(jwtResponseDto);
    }
}

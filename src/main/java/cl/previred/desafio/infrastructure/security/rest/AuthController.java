package cl.previred.desafio.infrastructure.security.rest;

import cl.previred.desafio.infrastructure.security.rest.dtos.AuthRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController implements IAuthController{
    @PostMapping("/login")
    public void auth(@Valid @RequestBody AuthRequest authRequest) {
    }
}

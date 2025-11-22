package com.yoserp1.tareas.service.implementation;

import com.yoserp1.tareas.dto.JwtResponseDTO;
import com.yoserp1.tareas.dto.LoginRequestDTO;
import com.yoserp1.tareas.exception.AplicacionException;
import com.yoserp1.tareas.security.JwtUtils;
import com.yoserp1.tareas.service.IAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService implements IAuthService {

  @Autowired private AuthenticationManager authenticationManager;

  @Autowired private JwtUtils jwtUtil;

  @Autowired private UserDetailsService userDetailsService;

  @Override
  public JwtResponseDTO login(LoginRequestDTO request) {
    try {
      Authentication authentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(request.username(), request.password()));

      SecurityContextHolder.getContext().setAuthentication(authentication);
      final UserDetails userDetails = (UserDetails) authentication.getPrincipal();

      if (!userDetails.isEnabled()) {
        log.error("Usuario {} está inactivo", request.username());
        throw AplicacionException.validation("Usuario inactivo");
      }

      final String token = jwtUtil.generateToken(userDetails);

      log.info("Usuario {} autenticado correctamente", request.username());
      return new JwtResponseDTO(token, userDetails.getUsername());

    } catch (BadCredentialsException ex) {
      log.error("Credenciales inválidas para usuario {}", request.username());
      throw AplicacionException.validation("Credenciales incorrectas");
    } catch (Exception ex) {
      log.error("Error inesperado al autenticar usuario {}", request.username(), ex);
      throw AplicacionException.operation("Error en autenticación", ex);
    }
  }

  @Override
  public LoginRequestDTO getPerfil(String username) {
    try {
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);

      if (!userDetails.isEnabled()) {
        log.error("Usuario {} está inactivo al consultar perfil", username);
        throw AplicacionException.validation("Usuario inactivo");
      }

      log.info("Perfil obtenido correctamente para usuario {}", username);

      return new LoginRequestDTO(userDetails.getUsername(), "********");

    } catch (UsernameNotFoundException ex) {
      log.error("Usuario {} no encontrado al consultar perfil", username);
      throw AplicacionException.notFound("Usuario", username);
    } catch (Exception ex) {
      log.error("Error inesperado al consultar perfil del usuario {}", username, ex);
      throw AplicacionException.operation("Error al obtener el perfil del usuario", ex);
    }
  }
}

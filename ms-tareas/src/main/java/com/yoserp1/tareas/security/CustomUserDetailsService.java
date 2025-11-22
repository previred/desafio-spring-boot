package com.yoserp1.tareas.security;

import com.yoserp1.tareas.jpa.entity.UsuarioEntity;
import com.yoserp1.tareas.jpa.repository.UsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UsuarioRepository usuarioRepository;

  @Autowired
  public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
    this.usuarioRepository = usuarioRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UsuarioEntity usuario =
        usuarioRepository
            .findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

    List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

    return User.withUsername(usuario.getUsername())
        .password(usuario.getPassword())
        .authorities(authorities)
        .accountExpired(false)
        .accountLocked(false)
        .credentialsExpired(false)
        .disabled(!usuario.isActivo())
        .build();
  }
}

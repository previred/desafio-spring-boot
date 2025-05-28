package com.nuevospa.security;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nuevospa.model.Usuario;
import com.nuevospa.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService  {
    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreoAndActivoTrue(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con correo: " + correo));

        return new org.springframework.security.core.userdetails.User(
                usuario.getCorreo(),
                usuario.getContrasena(),
                new ArrayList<>()
        );
    }
}

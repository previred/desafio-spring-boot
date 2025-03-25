package com.gestion.tarea.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestion.tarea.dto.UsuarioDto;
import com.gestion.tarea.repository.UsuariosRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UsuariosRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UsuarioDto user = userRepository.findByUserName(username)
        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con userName: " + username));

    return UserDetailsImpl.build(user);
  }

}

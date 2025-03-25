package com.gestion.tarea.security.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.gestion.tarea.dto.UsuarioDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;

  private Long id;

  private String userName;

  private String rol;

  @JsonIgnore
  private String pass;

  private boolean habilitado;

  public UserDetailsImpl(Long id, String userName, String rol, String pass,
      boolean habilitado) {
    this.id = id;
    this.userName = userName;
    this.rol = rol;
    this.pass = pass;
    this.habilitado = habilitado;
  }

  public static UserDetailsImpl build(UsuarioDto user) {

    return new UserDetailsImpl(
        user.getId(), 
        user.getUserName(),
        user.getRol(),
        user.getPass(),
        user.getHabilitado());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

    List<GrantedAuthority> authorities = List.of();
    return authorities;
  }


  public Long getId() {
    return id;
  }

  public String getUserName() {
    return userName;
  }

  @Override
  public String getUsername() {
    return userName;
  }


  @Override
  public String getPassword() {
    return pass;
  }


  public String getRol() {
    return rol;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }
}

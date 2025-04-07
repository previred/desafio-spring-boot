package cl.previred.desafio.infrastructure.security.service;

import cl.previred.desafio.domain.ports.in.usecases.usuario.IFindUsuarioByUsernameUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JpaUserDetailService implements UserDetailsService {
    private final IFindUsuarioByUsernameUseCase findUsuarioByUsernameUseCase;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var usuario = findUsuarioByUsernameUseCase.execute(username);
        List<GrantedAuthority> role = Collections.singletonList(new SimpleGrantedAuthority(usuario.getRole()));

        return new User(
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.isEnabled(),
                true,
                true,
                true,
                role
        );
    }
}

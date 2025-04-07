package cl.previred.desafio.infrastructure.security.filters;

import cl.previred.desafio.infrastructure.db.jpa.entities.UsuarioEntity;
import cl.previred.desafio.infrastructure.security.exceptions.InvalidLoginException;
import cl.previred.desafio.infrastructure.security.rest.dtos.AuthResponse;
import cl.previred.desafio.infrastructure.security.filters.utils.JwtProperties;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;



@RequiredArgsConstructor
public class JwtAuthFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        String username = null;
        String password = null;
        try {
            var user=new ObjectMapper().readValue(request.getInputStream(), UsuarioEntity.class);
            username=user.getUsername();
            password=user.getPassword();
        } catch (StreamReadException e) {
            e.printStackTrace();
        } catch (DatabindException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult)
            throws IOException, ServletException {
        String username=((org.springframework.security.core.userdetails.User)authResult.getPrincipal()).getUsername();
        var roles= new java.util.ArrayList<>(authResult.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList());
        if(roles.isEmpty()){
            roles.add("");
        }
        String token=Jwts.builder()
                .signWith(JwtProperties.JWT_KEY)
                .header().type("JWT").and()
                .subject(username)
                .claim("role", roles.get(0))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+3600000*8))
                .compact();

        response.getWriter().write(new ObjectMapper().writeValueAsString(new AuthResponse(token,username)));
        response.setStatus(200);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        throw new InvalidLoginException(failed.getMessage());
    }
}

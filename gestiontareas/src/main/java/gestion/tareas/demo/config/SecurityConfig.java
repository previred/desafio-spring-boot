package gestion.tareas.demo.config;

import gestion.tareas.demo.security.JwtAuthenticationFilter;
import gestion.tareas.demo.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UsuarioService usuarioService;

    public SecurityConfig(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                    .authorizeRequests()
                    .requestMatchers("/auth/login","/swagger-ui/**","/swagger-ui.html", "/v2/api-docs","/v3/api-docs/**", "/swagger-resources/**", "/webjars/**", "/h2-console/**")
                    .permitAll() // Permitir acceso público a /auth/login
                    .requestMatchers(HttpMethod.GET, "/tareas/**").hasAnyRole("USER", "ADMIN")
                    .requestMatchers(HttpMethod.POST, "/tareas").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/tareas/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/tareas/**").hasRole("ADMIN")
                    .requestMatchers("/tareas/**").authenticated()
                    .anyRequest().authenticated() // Requiere autenticación para cualquier otra ruta
                .and()
                .csrf().disable()
                .addFilterBefore(new JwtAuthenticationFilter(usuarioService), UsernamePasswordAuthenticationFilter.class); // Agregar filtro JWT

        return http.build();
    }
}

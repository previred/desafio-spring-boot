package gestion.tareas.demo.infrastructure.configuration;

import gestion.tareas.demo.application.get.usuario.GetUsuarioGateway;
import gestion.tareas.demo.infrastructure.configuration.exception.CustomAccessDeniedHandler;
import gestion.tareas.demo.infrastructure.security.JwtAuthenticationFilter;
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

    private final GetUsuarioGateway getUsuarioGateway;

    public SecurityConfig(GetUsuarioGateway getUsuarioGateway) {
        this.getUsuarioGateway = getUsuarioGateway;
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .requestMatchers(
                        "/auth/login",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v2/api-docs",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/h2-console/**"
                ).permitAll()
                .requestMatchers(HttpMethod.GET, "/tareas/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/tareas").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/tareas/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/tareas/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler())
                .and()
                .csrf().disable()
                .headers().frameOptions().disable() // Para H2 Console
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(getUsuarioGateway),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

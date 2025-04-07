package cl.previred.desafio.infrastructure.security;

import cl.previred.desafio.infrastructure.security.filters.JwtAuthFilter;
import cl.previred.desafio.infrastructure.security.filters.JwtValidFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
public class SpringSecurityConfig {
    private final AuthenticationConfiguration authenticationConfiguration;
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
        http
                .authorizeHttpRequests((authz)-> authz
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/v3/api-docs.yaml",
                                "/v3/api-docs",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilter(new JwtAuthFilter(authenticationConfiguration.getAuthenticationManager()))
                .addFilter(new JwtValidFilter(authenticationConfiguration.getAuthenticationManager()))
                .sessionManagement(session-> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }


}
package com.nuevospa.tasksmanagement.config;

import com.nuevospa.tasksmanagement.security.JwtAuthEntryPoint;
import com.nuevospa.tasksmanagement.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration for the application.
 * Defines authentication manager, password encoder, security filter chain, and JWT filter.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthEntryPoint unauthorizedHandler;
    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 1. Allows public access to AUTHENTICATION ENDPOINTS
                        .requestMatchers("/api/v1/auth/**").permitAll()

                        // 2. Allows public access to SWAGGER UI and OpenAPI resources
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/swagger-ui.html/**").permitAll()
                        .requestMatchers("/api-docs/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/swagger-resources/**").permitAll()
                        .requestMatchers("/webjars/**").permitAll()

                        // 3. Allows public access to H2 Console
                        .requestMatchers("/h2-console/**").permitAll()

                        // 4. Specific rules for your API (Roles or authenticators)
                        // User management endpoints: only for ADMINs
                        .requestMatchers(HttpMethod.POST, "/api/v1/users").hasRole("ADMIN") // Create user
                        .requestMatchers(HttpMethod.PUT, "/api/v1/users/**").hasRole("ADMIN") // Update user
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasRole("ADMIN") // Delete user
                        .requestMatchers(HttpMethod.GET, "/api/v1/users/**").hasAnyRole("ADMIN") // Get user (Admin only)
                        // Task management endpoints: for authenticated users
                        .requestMatchers("/api/v1/tasks/**").authenticated() // All task operations require authentication

                        // 5. LAST! All other requests require authentication.
                        .anyRequest().authenticated()
                );

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
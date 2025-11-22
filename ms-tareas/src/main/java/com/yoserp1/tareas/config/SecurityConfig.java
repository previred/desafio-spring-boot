package com.yoserp1.tareas.config;

import com.yoserp1.tareas.security.CustomUserDetailsService;
import com.yoserp1.tareas.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

  @Autowired private CustomUserDetailsService userDetailsService;
  @Autowired private JwtAuthenticationFilter jwtAuthenticationFilter;

  private static final String[] SWAGGER_WHITELIST = {
    "/v3/api-docs/**",
    "/swagger-ui/**",
    "/swagger-ui.html",
    "/swagger-resources/**",
    "/api-docs/**",
    "/api-docs/swagger-config",
    "/webjars/**",
    "/actuator/**",
    "/h2-console/**",
  };

  @Bean
  public SecurityFilterChain securityFilterChain(
      HttpSecurity http, AuthenticationManager authManager) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(SWAGGER_WHITELIST)
                    .permitAll()
                    .requestMatchers("/api/auth/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
        .authenticationManager(authManager)
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    return auth.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}

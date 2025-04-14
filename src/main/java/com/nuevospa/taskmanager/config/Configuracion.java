package com.nuevospa.taskmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import com.nuevospa.taskmanager.security.InterceptorRequest;

@Configuration
@EnableWebSecurity
public class Configuracion {

	private final InterceptorRequest interceptorRequest;

	public Configuracion(InterceptorRequest interceptorRequest) {
		this.interceptorRequest = interceptorRequest;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {    

		
        http
        .csrf().disable()
        .authorizeRequests()
        .requestMatchers("/api/auth/login").permitAll()
        .requestMatchers("/h2-console/**").permitAll()
        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .headers()
            .frameOptions().disable()
        .and()
        .addFilterBefore(interceptorRequest, UsernamePasswordAuthenticationFilter.class);

    return http.build();
	}
}
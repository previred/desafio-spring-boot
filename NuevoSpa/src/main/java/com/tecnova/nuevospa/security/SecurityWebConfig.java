package com.tecnova.nuevospa.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.tecnova.nuevospa.constant.ConstantsApp;

import lombok.RequiredArgsConstructor;

/**
 * Configuration class in charge of the application's security settings.
 * 
 * @author GCR.</br>
 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityWebConfig {

	private final AuthenticationEntryPointConfig authenticationEntryPoint;
	private final AppAuthenticationProvider appAuthenticationProvider;
	private final Securityfilter securityfilter;

	/**
	 * Method in charge of initializing and configuring the Bean
	 * {@link SecurityFilterChain}, in charge of configuring security in the
	 * application.
	 * 
	 * @param http {@link HttpSecurity}: Http security to config.
	 * @return SecurityFilterChain {@link SecurityFilterChain}: Bean initialized.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
				.cors(basicCors -> basicCors.configurationSource(corsConfigurationSource()))
				.authorizeHttpRequests(
						authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
								.requestMatchers(HttpMethod.DELETE).hasRole(ConstantsApp.ROLE_USER)
								.requestMatchers(ConstantsApp.ACCESS_SWAGGER.toArray(new String[0]))
								.hasAnyRole(ConstantsApp.ROLES_SWAGGER.toArray(new String[0]))
								.requestMatchers(ConstantsApp.ENDPOINTS_PERMITALL.toArray(new String[0])).permitAll()
								.anyRequest().authenticated())

				.httpBasic(httpSecurityHttpBasicConfigurer -> httpSecurityHttpBasicConfigurer
						.authenticationEntryPoint(authenticationEntryPoint))
				.sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(securityfilter, UsernamePasswordAuthenticationFilter.class)
				.authenticationProvider(appAuthenticationProvider);
		return http.build();
	}

	/**
	 * Method in charge of initializing and configuring the Bean
	 * {@link CorsConfigurationSource}, in charge of configuring the Cors allowed in
	 * the application.
	 * 
	 * @return CorsConfigurationSource {@link CorsConfigurationSource}: Bean
	 *         initialized.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(List.of("*"));
		config.setAllowedMethods(ConstantsApp.ALLOWED_METHODS);
		config.setAllowCredentials(true);
		config.setAllowedHeaders(ConstantsApp.getFullHeaders());
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration(ConstantsApp.GENERAL_ACCESS_PATH, config);
		return source;
	}

}

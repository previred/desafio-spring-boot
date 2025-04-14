package com.nuevospa.taskmanager.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class InterceptorRequest extends OncePerRequestFilter {

	@Value("${jwt.secret}")
	private String secretKey;
	
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    	 System.out.println("Se valida ruta de la peticion: ["+request.getRequestURI()+"]");
    	 
         if (request.getRequestURI().equals("/api/auth/login") || request.getRequestURI().startsWith("/h2-console") ||    
             request.getRequestURI().startsWith("/swagger-ui") ||
             request.getRequestURI().startsWith("/v3/api-docs") ||
             request.getRequestURI().startsWith("/swagger-resources") ||
             request.getRequestURI().startsWith("/webjars")) {
        	 
        	 System.out.println("Excepcion de peticion");
             filterChain.doFilter(request, response); 
             return;
         }

         String token = request.getHeader("Authorization");


         if (token == null || !token.startsWith("Bearer ")) {
             response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token no presente o formato incorrecto");
             return;
         }

         token = token.substring(7); 

         try {
             Claims claims = Jwts.parser()
                     .setSigningKey(secretKey)
                     .parseClaimsJws(token)
                     .getBody();

             Authentication authentication = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, new ArrayList<>());
             SecurityContextHolder.getContext().setAuthentication(authentication);

         } catch (Exception e) {
             System.out.println("Token no válido o expirado...");
             response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token no válido o expirado");
             return;
         }

         filterChain.doFilter(request, response); // Continuar con la cadena de filtros
    }
}

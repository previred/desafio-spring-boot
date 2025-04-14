package com.nuevospa.taskmanager.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nuevospa.taskmanager.service.LoginService;
import com.nuevospa.taskmanager.dto.LoginResponse;
import com.nuevospa.taskmanager.entity.Usuario;
import com.nuevospa.taskmanager.util.NuevoSPAParams;
import com.nuevospa.taskmanager.entity.Usuario;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

	@Value("${jwt.secret}")
	private String secretKey;

	private final UsuarioService usuarioService;

	public LoginServiceImpl(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public LoginResponse login(String username, String password) {

		try {

			LoginResponse response = new LoginResponse();
			response.setCodigo(NuevoSPAParams.CODIGO_OK);
			response.setDescripcion(NuevoSPAParams.DESCRIPCION_OK);

			Optional<Usuario> usuarioOptional = usuarioService.findByNombre(username);

			if (usuarioOptional.isEmpty()) {

				response.setCodigo(NuevoSPAParams.CODIGO_USUARIO_NO_ENCONTRADO);
				response.setDescripcion(NuevoSPAParams.DESC_USUARIO_NO_ENCONTRADO);

				return response;
			}

			Usuario usuario = usuarioOptional.get();

			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			if (!passwordEncoder.matches(password, usuario.getPassword())) {
				System.out.println("Password no match!! ");

				response.setCodigo(NuevoSPAParams.CODIGO_CREDENCIALES_NO_VALIDAS);
				response.setDescripcion(NuevoSPAParams.DESC_CREDENCIALES_NO_VALIDAS);

				return response;
			}

			return generateToken(usuario);

		}catch(Exception e) {
			e.printStackTrace();

			LoginResponse errorTareaLogin = new LoginResponse();
			errorTareaLogin.setCodigo(NuevoSPAParams.CODIGO_ERROR);
			errorTareaLogin.setDescripcion(NuevoSPAParams.DESCRIPCION_ERROR);

			return errorTareaLogin;
		}



	}

	private LoginResponse generateToken(Usuario usuario) {

		LoginResponse response = new LoginResponse();
		response.setCodigo(NuevoSPAParams.CODIGO_OK);
		response.setDescripcion(NuevoSPAParams.DESCRIPCION_OK);

		String token = null;

		token = Jwts.builder()
				.setSubject(usuario.getNombre()) 
				.claim("id", usuario.getId()) 
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 3600000)) // expiración del token (1 hora)
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();

		if (token == null || token.isEmpty()) {
			response.setCodigo(NuevoSPAParams.CODIGO_ERROR_GENERACION_TOKEN);
			response.setDescripcion(NuevoSPAParams.DESC_ERROR_GENERACION_TOKEN);
			return response;
		}

		response.setToken(token);

		return response;
	}

}
package com.nuevospa.taskmanager.controller;

import com.nuevospa.taskmanager.dto.LoginRequest;
import com.nuevospa.taskmanager.dto.LoginResponse;
import com.nuevospa.taskmanager.service.LoginService;
import com.nuevospa.taskmanager.service.UsuarioService;
import com.nuevospa.taskmanager.util.NuevoSPAParams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@Autowired
	private UsuarioService usuarioService;
	
	@Operation(
			summary = "Autenticación de usuarios",
			description = "Permite a un usuario autenticarse con su nombre y contraseña y obtener un token JWT"
			)
	@ApiResponses(value = {
			@ApiResponse(responseCode = NuevoSPAParams.CODIGO_OK, description = NuevoSPAParams.DESCRIPCION_OK),
			@ApiResponse(responseCode = NuevoSPAParams.CODIGO_USUARIO_NO_ENCONTRADO, description = NuevoSPAParams.DESC_USUARIO_NO_ENCONTRADO),
			@ApiResponse(responseCode = NuevoSPAParams.CODIGO_CREDENCIALES_NO_VALIDAS, description = NuevoSPAParams.DESC_CREDENCIALES_NO_VALIDAS),
			@ApiResponse(responseCode = NuevoSPAParams.CODIGO_ERROR, description = NuevoSPAParams.DESCRIPCION_ERROR)
	})
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

		System.out.println("Inicio login");

		LoginResponse response = new LoginResponse();
		response.setCodigo(NuevoSPAParams.CODIGO_OK);
		response.setDescripcion(NuevoSPAParams.DESCRIPCION_OK);

		response = loginService.login(loginRequest.getNombreUsuario(), loginRequest.getPassword());

		return ResponseEntity.ok(response);
	}
}
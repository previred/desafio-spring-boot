package com.cifuentes.cristobal.gestion.service;

import com.cifuentes.cristobal.gestion.model.entity.User;

/**
 * Servicio que define operaciones relacionadas con la entidad User.
 *
 * Este servicio se encarga de encapsular la lógica de acceso a datos
 * y operaciones de negocio relacionadas con usuarios.
 */
public interface UserService {

	 /**
     * Busca un usuario por su email.
     *
     * @param email Email del usuario a buscar.
     * @return Objeto User correspondiente al email.
     * @throws RuntimeException si el usuario no se encuentra.
     */
	User getUserByEmail(String email);

}

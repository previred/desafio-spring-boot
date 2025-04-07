package cl.previred.desafio.infrastructure.exceptions.catalog;

import lombok.Getter;

@Getter
public enum CatalogException {
    ENTITY_NOT_FOUND("ERR_001", "Entidad no encontrada."),
    USERNAME_NOT_FOUND("ERR_002", "Username no encontrado."),
    INVALID_LOGIN("ERR_003", "Problemas al iniciar sesion."),
    VALIDATION_REQUEST("ERR_004", "Error en la validacion de los datos."),
    GENERIC_ERROR("ERR_GEN_001", "Error genérico."),;

    private final String code;
    private final String message;

    CatalogException(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
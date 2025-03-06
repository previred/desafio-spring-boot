package com.provired.challenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para información del usuario")
public class UserDTO {

    @JsonProperty("id_user")
    @Schema(description = "ID único del usuario", example = "1")
    private Long id;

    @JsonProperty("username")
    @Schema(description = "Nombre de usuario", example = "Ronny")
    private String username;
}
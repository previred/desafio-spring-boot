package com.tecnova.nuevospa.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * Dto Response that contains the information about the users auth.<br>
 * 
 * @author GCR.</br>
 *          Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
@Builder
@Schema(description = "Dto response that contains data about user.")
@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthResponseDto(
		@Schema(description = "username associated to user.", example = "gcr")
		String username,
		@Schema(description = "Code associated to user.", example = "001")
		String code,
		@Schema(description = "token access associated to user.", example = "123213ssad23123123")
		String tokenAccess,
		@Schema(description = "Expiration token.", example = "2025-03-01 15:00:00")
		LocalDateTime tokenExpired
		) {

}

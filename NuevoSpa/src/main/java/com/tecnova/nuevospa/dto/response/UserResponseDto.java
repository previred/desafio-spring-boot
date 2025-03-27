package com.tecnova.nuevospa.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
/**
 * Dto Response that contains the information about the users.<br>
 * 
 * @author GCR.</br>
 *          Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
@Builder
@Schema(description = "Dto response that contains data about user.")
@JsonIgnoreProperties(ignoreUnknown = true)
public record UserResponseDto(
		@Schema(description = "username associated to user.", example = "gcr")
		String username,
		@Schema(description = "Code associated to user.", example = "001")
		String code
		
		) {

}

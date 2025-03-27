package com.tecnova.nuevospa.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
/**
 * Dto Response that contains the information about the status tasks.<br>
 * 
 * @author GCR.</br>
 *          Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
@Builder
@Schema(description = "Dto response that contains data about status task.")
@JsonIgnoreProperties(ignoreUnknown = true)
public record StatusTaskResponseDto(
		@Schema(description = "Name status task.", example = "In Progress")
		String name,
		@Schema(description = "Code status task.", example = "PROCESS")
		String code
		
		) {

}

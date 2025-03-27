package com.tecnova.nuevospa.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
/**
 * Dto Response that contains the information about the tasks.<br>
 * 
 * @author GCR.</br>
 *          Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
@Builder
@Schema(description = "Dto response that contains data about task.")
@JsonIgnoreProperties(ignoreUnknown = true)
public record TaskResponseDto(
		@Schema(description = "Identifier task.", example = "1")
		Long id,
		@Schema(description = "Name task.", example = "Task 1")
		String name,
		@Schema(description = "Code task.", example = "001")
		String code,
		@Schema(description = "Description task.", example = "task in progress")
		String description,
        @Schema(description = "Start date task", example = "2000-01-01")
		LocalDateTime startTask,
		@Schema(description = "End date task", example = "2000-01-15")
		LocalDateTime endTask,
		@Schema(description = "User related with the task", example = "{name:gcr}")
		UserResponseDto user, 
		@Schema(description = "Status related with the task", example = "{name:PROGRESS}")
		StatusTaskResponseDto statusTask
		
		) {

}

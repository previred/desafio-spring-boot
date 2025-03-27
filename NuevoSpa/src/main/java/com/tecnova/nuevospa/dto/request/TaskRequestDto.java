package com.tecnova.nuevospa.dto.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
/**
 * Dto Request that request the information about the user.<br>
 * 
 * @author GCR.</br>
 *          Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
@Builder
@Schema(description = "Dto request that contains data about status task.")
@JsonIgnoreProperties(ignoreUnknown = true)
public record TaskRequestDto(
		@Schema(description = "Identifier task.", example = "1")
		Long id,
		@Schema(description = "Name task.", example = "Task 1")
		@NotNull(message = "{app.message.valid.not.null}")
        @NotBlank(message = "{app.message.valid.not.black}")
        @Size(message = "{app.message.valid.size.min.max}", min = 1, max = 50)
		String name,
		@Schema(description = "Description task.", example = "task in progress")
        @NotBlank(message = "{app.message.valid.not.black}")
		String description,
        @Schema(description = "Start date task", example = "2000-01-01")
		@NotNull(message = "{app.message.valid.not.null}")
		LocalDateTime startTask,
		@Schema(description = "End date task", example = "2000-01-15")
		LocalDateTime endTask,
		@Schema(description = "User code related with the task", example = "gcr")
		@NotNull(message = "{app.message.valid.not.null}")
        @NotBlank(message = "{app.message.valid.not.black}")
		String userCode, 
		@Schema(description = "Status code related with the task", example = "PROGRESS")
		@NotNull(message = "{app.message.valid.not.null}")
        @NotBlank(message = "{app.message.valid.not.black}")
		String statusTaskCode
		
		) {

}

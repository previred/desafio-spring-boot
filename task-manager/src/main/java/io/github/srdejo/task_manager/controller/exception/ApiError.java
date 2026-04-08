package io.github.srdejo.task_manager.controller.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiError(int status, String message, List<String> details) {
}

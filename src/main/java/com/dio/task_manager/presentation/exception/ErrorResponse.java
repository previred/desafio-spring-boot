package com.dio.task_manager.presentation.exception;

public record ErrorResponse(String error, String message, int status) {
}

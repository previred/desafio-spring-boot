package com.dio.task_manager.presentation.dto;

import com.dio.task_manager.application.dto.TaskRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TaskRequestValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldFailWhenTitleIsBlank() {
        TaskRequest request = new TaskRequest(" ", "Descripción válida", LocalDate.now(), 1L, null);
        Set<ConstraintViolation<TaskRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("title")));
    }

    @Test
    void shouldFailWhenDueDateIsNull() {
        TaskRequest request = new TaskRequest("Título", "Descripción", null, 1L, null);
        Set<ConstraintViolation<TaskRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("dueDate")));
    }

    @Test
    void shouldPassWhenAllFieldsAreValid() {
        TaskRequest request = new TaskRequest("Título válido", "Descripción válida", LocalDate.now(), 1L, null);
        Set<ConstraintViolation<TaskRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }
}
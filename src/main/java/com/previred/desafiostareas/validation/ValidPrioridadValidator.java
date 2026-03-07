package com.previred.desafiostareas.validation;

import com.previred.desafiostareas.controller.dto.TareaRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidPrioridadValidator implements ConstraintValidator<ValidPrioridad, TareaRequest.PrioridadEnum> {
    @Override
    public boolean isValid(TareaRequest.PrioridadEnum value, ConstraintValidatorContext context) {
        if (value == null) return false;
        int id = value.getId();
        return id == 1 || id == 2 || id == 3;
    }
}

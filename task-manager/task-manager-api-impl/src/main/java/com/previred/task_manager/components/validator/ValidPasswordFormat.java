package com.previred.task_manager.components.validator;


import com.previred.task_manager.components.validator.impl.ValidatorPasswordFormat;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = ValidatorPasswordFormat.class)
@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface ValidPasswordFormat {
    String message() default "La contraseña debe tener al menos 8 caracteres, incluir mayúsculas, minúsculas, dígitos y un carácter especial";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

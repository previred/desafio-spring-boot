package com.previred.desafiostareas.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = ValidPrioridadValidator.class)
@Target({ FIELD })
@Retention(RUNTIME)
public @interface ValidPrioridad {
    String message() default "La prioridad debe ser BAJA, MEDIA o ALTA (id 1, 2 o 3)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

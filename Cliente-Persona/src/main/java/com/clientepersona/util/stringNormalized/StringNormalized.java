package com.clientepersona.util.stringNormalized;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = StringNormalizedValidator.class)
public @interface StringNormalized {

    String message() default "No se permiten caracteres especiales como valores de entrada";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

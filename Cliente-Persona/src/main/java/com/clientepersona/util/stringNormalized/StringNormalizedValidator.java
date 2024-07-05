package com.clientepersona.util.stringNormalized;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class StringNormalizedValidator implements ConstraintValidator<StringNormalized, String> {
    @Override
    public void initialize(StringNormalized constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        List<String> invalidCharacters = List.of("=", ";", "--", "<", ">", "^");

        if (value == null || value.isEmpty()) {
            return true;
        }

        for (String e : invalidCharacters) {
            if (value.contains(e)) return false;
        }

        return true;

    }


}

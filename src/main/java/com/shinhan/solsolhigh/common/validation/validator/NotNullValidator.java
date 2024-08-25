package com.shinhan.solsolhigh.common.validation.validator;

import com.shinhan.solsolhigh.common.exception.NullPointerException;
import com.shinhan.solsolhigh.common.validation.annotation.NotNull;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotNullValidator implements ConstraintValidator<NotNull, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null)
            throw new NullPointerException();
        return true;
    }
}
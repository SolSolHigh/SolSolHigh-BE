package com.shinhan.solsolhigh.promise.validation.validator;

import com.shinhan.solsolhigh.promise.exception.PromiseTicketDescriptionMismatchException;
import com.shinhan.solsolhigh.promise.validation.annotation.PromiseTicketDescription;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PromiseTicketDescriptionValidator implements ConstraintValidator<PromiseTicketDescription, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value != null && (value.trim().isEmpty() || value.length() > 64))
            throw new PromiseTicketDescriptionMismatchException();
        return true;
    }
}
package com.shinhan.solsolhigh.user.validation.validator;

import com.shinhan.solsolhigh.user.exception.UserBirthdayMismatchException;
import com.shinhan.solsolhigh.user.validation.annotation.Birthday;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class BirthdayValidator implements ConstraintValidator<Birthday, LocalDate> {

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext constraintValidatorContext) {
        if (value != null && LocalDate.now().isBefore(value))
            throw new UserBirthdayMismatchException();
        return true;
    }
}
package com.shinhan.solsolhigh.user.validation.validator;

import com.shinhan.solsolhigh.user.exception.UserEmailMismatchException;
import com.shinhan.solsolhigh.user.validation.annotation.Email;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<Email, String> {
    private static final Pattern pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value != null && !pattern.matcher(value).matches())
            throw new UserEmailMismatchException();
        return true;
    }
}
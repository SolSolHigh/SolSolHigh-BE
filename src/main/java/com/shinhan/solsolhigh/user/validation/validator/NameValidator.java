package com.shinhan.solsolhigh.user.validation.validator;

import com.shinhan.solsolhigh.user.exception.UserNameMismatchException;
import com.shinhan.solsolhigh.user.validation.annotation.Name;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class NameValidator implements ConstraintValidator<Name, String> {
    private static final Pattern pattern = Pattern.compile("^(?![ㄱ-ㅎㅏ-ㅣ]+$)[가-힣]{2,8}$");
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value != null && !pattern.matcher(value).matches())
            throw new UserNameMismatchException();
        return true;
    }
}
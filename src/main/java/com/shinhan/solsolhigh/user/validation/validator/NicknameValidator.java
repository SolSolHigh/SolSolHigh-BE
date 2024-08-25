package com.shinhan.solsolhigh.user.validation.validator;

import com.shinhan.solsolhigh.user.exception.UserNicknameMismatchException;
import com.shinhan.solsolhigh.user.validation.annotation.Nickname;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class NicknameValidator implements ConstraintValidator<Nickname, String> {
    private static final Pattern pattern = Pattern.compile("^(?![ㄱ-ㅎㅏ-ㅣ]+$)[가-힣A-Za-z]{2,8}$");
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value != null && !pattern.matcher(value).matches())
            throw new UserNicknameMismatchException();
        return true;
    }
}
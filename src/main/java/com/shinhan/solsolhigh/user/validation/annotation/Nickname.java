package com.shinhan.solsolhigh.user.validation.annotation;

import com.shinhan.solsolhigh.user.validation.validator.NicknameValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NicknameValidator.class)
public @interface Nickname {
    String message() default "닉네임 형식 검사";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
}
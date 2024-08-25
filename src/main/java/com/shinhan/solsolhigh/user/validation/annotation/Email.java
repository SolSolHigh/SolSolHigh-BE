package com.shinhan.solsolhigh.user.validation.annotation;

import com.shinhan.solsolhigh.user.validation.validator.EmailValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
public @interface Email {
    String message() default "이메일 형식 검사";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
}
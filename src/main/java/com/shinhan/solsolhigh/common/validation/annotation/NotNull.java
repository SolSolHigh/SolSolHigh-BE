package com.shinhan.solsolhigh.common.validation.annotation;

import com.shinhan.solsolhigh.common.validation.validator.NotNullValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotNullValidator.class)
public @interface NotNull {
    String message() default "빈 값 검사";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
}
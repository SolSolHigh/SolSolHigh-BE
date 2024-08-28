package com.shinhan.solsolhigh.promise.validation.annotation;

import com.shinhan.solsolhigh.promise.validation.validator.PromiseTicketImageValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PromiseTicketImageValidator.class)
public @interface PromiseTicketImage {
    String message() default "약속권 이미지 검사";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
}
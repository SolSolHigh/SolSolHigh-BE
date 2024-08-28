package com.shinhan.solsolhigh.promise.validation.annotation;

import com.shinhan.solsolhigh.promise.validation.validator.PromiseTicketDescriptionValidator;
import com.shinhan.solsolhigh.user.validation.validator.BirthdayValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PromiseTicketDescriptionValidator.class)
public @interface PromiseTicketDescription {
    String message() default "약속권 본문 검사";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
}
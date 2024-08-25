package com.shinhan.solsolhigh.common.aop.annotation;

import com.shinhan.solsolhigh.user.domain.User.Type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorized {
    Type allowed();
}
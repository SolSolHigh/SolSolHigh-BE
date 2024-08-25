package com.shinhan.solsolhigh.common.aop.aspect;

import com.shinhan.solsolhigh.common.aop.annotation.Authorized;
import com.shinhan.solsolhigh.common.exception.UserBadRequestException;
import com.shinhan.solsolhigh.user.domain.User;
import com.shinhan.solsolhigh.user.domain.UserPrinciple;
import com.shinhan.solsolhigh.user.domain.User.Type;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthorizedAspect {

    @Before("@annotation(com.shinhan.solsolhigh.common.aop.annotation.Authorized)")
    public void checkAuthorized(JoinPoint joinPoint) {
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        MethodSignature ms = (MethodSignature)joinPoint.getSignature();
        Authorized authorized = ms.getMethod().getAnnotation(Authorized.class);
        Type type = userPrinciple.getType();

        if(type != authorized.allowed())
            throw new UserBadRequestException();
    }
}
package com.shinhan.solsolhigh.common.util;

import org.hibernate.proxy.HibernateProxy;

public class JPAUtils {
    private JPAUtils(){};

    public static <T, U> T typeCast(Class<T> type, U entity) {
        T result;
        if (entity instanceof HibernateProxy)
            result = (T) ((HibernateProxy) entity).getHibernateLazyInitializer().getImplementation();
        else
            result = (T) entity;
        return result;
    }
}
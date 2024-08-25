package com.shinhan.solsolhigh.user.domain;

import lombok.Getter;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;
import java.util.Objects;

@Getter
public class Gender implements Serializable{
    private Type type;

    public static final Type MALE = Type.MALE;
    public static final Type FEMALE = Type.FEMALE;

    public enum Type {
        FEMALE("F"), MALE("M");

        private final String value;
        Type(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

    public Gender(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public static Gender of(String val) {
        if(val == null || val.isEmpty()) return null;

        if("F".equalsIgnoreCase(val))
            return new Gender(Type.FEMALE);

        if("M".equalsIgnoreCase(val))
            return new Gender(Type.MALE);

        throw new IllegalArgumentException("Invalid gender: " + val);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if( obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Gender gender = (Gender) obj;
        return type.equals(gender.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type.hashCode());
    }
}

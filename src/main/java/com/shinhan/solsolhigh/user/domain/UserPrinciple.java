package com.shinhan.solsolhigh.user.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

@Getter
@Builder
public class UserPrinciple implements OAuth2User, Serializable {

    private Map<String, Object> attributes;
    private Integer id;
    private String name;
    private String email;
    private LocalDate birthday;
    private Class<?> userClass;
    private Gender gender;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}

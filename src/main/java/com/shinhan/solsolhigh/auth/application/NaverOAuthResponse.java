package com.shinhan.solsolhigh.auth.application;

import lombok.Builder;

import java.util.Map;

@Builder
public class NaverOAuthResponse implements OAuthResponse {

    private final Map<String, Object> attribute;

    private NaverOAuthResponse(Map<String, Object> attribute) {
        this.attribute = (Map<String, Object>) attribute.get("response");
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    @Override
    public String getEmail() {
        return attribute.get("email").toString() ;
    }

    @Override
    public String getName() {
        return attribute.get("name").toString();
    }

    public static NaverOAuthResponse from(Map<String, Object> attribute) {
        return new NaverOAuthResponse(attribute);
    }
}

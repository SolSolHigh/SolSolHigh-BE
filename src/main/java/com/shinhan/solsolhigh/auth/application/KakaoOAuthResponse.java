package com.shinhan.solsolhigh.auth.application;

import java.util.Map;

public class KakaoOAuthResponse implements OAuthResponse {
    private Map<String, Object> attribute;
    private Map<String, Object> kakaoAccountAttribute;

    private KakaoOAuthResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
        this.kakaoAccountAttribute = (Map<String, Object>) attribute.get("kakao_account");
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    @Override
    public String getEmail() {
        return kakaoAccountAttribute.get("email").toString();
    }

    @Override
    public String getName() {
        return kakaoAccountAttribute.get("name").toString();
    }

    public static KakaoOAuthResponse from(Map<String, Object> attribute) {
        return new KakaoOAuthResponse(attribute);
    }
}

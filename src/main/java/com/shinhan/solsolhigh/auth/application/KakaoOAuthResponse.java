package com.shinhan.solsolhigh.auth.application;

import java.util.Map;

public class KakaoOAuthResponse implements OAuthResponse {
    private Map<String, Object> attribute;
    private Map<String, Object> kakaoAccountAttribute;
    private Map<String, Object> profileAttribute;

    private KakaoOAuthResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
        this.kakaoAccountAttribute = (Map<String, Object>) attribute.get("kakao_account");
        this.profileAttribute = (Map<String, Object>) kakaoAccountAttribute.get("profile");
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
        return profileAttribute.get("nickname").toString();
    }

    public static KakaoOAuthResponse from(Map<String, Object> attribute) {
        return new KakaoOAuthResponse(attribute);
    }
}

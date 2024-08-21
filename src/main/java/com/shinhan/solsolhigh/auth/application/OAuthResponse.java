package com.shinhan.solsolhigh.auth.application;

public interface OAuthResponse {
    String getProvider();
    String getProviderId();
    String getEmail();
    String getName();
}

package com.shinhan.solsolhigh.security.config;

import com.shinhan.solsolhigh.springconfig.ServerConfig;
import com.shinhan.solsolhigh.user.exception.UserSignupNotCompletedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@AllArgsConstructor
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ServerConfig serverConfig;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        request.getSession().invalidate();
        if (exception.getCause() instanceof UserSignupNotCompletedException e) {
            String redirectUrl = UriComponentsBuilder.fromUriString(serverConfig.getFrontBase()+"/login")
                    .queryParam("status", e.getErrorConstantDefinition().getStatusCode())
                    .queryParam("code", request.getAttribute("code"))
                    .build()
                    .encode()
                    .toUriString();
            response.sendRedirect(redirectUrl);
        }
    }
}

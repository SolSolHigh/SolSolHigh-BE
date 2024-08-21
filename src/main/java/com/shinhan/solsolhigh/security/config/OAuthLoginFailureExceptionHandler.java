package com.shinhan.solsolhigh.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shinhan.solsolhigh.user.exception.UserSignupNotCompletedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuthLoginFailureExceptionHandler implements AuthenticationFailureHandler {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        if (exception.getCause() instanceof UserSignupNotCompletedException e) {
            request.getSession().invalidate();
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(e.getErrorConstantDefinition().getHttpStatus().value());
            String jsonBody = mapper.writeValueAsString(e.getErrorConstantDefinition().toErrorResponseEntity().getBody());
            response.getWriter().write(jsonBody);
        }
    }
}

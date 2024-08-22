package com.shinhan.solsolhigh.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shinhan.solsolhigh.common.exception.ErrorConstantDefinition;
import com.shinhan.solsolhigh.common.exception.GlobalErrorConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper mapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ErrorConstantDefinition ec = GlobalErrorConstants.USER_UNAUTHORIZED;
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(ec.getHttpStatus().value());
        String jsonBody = mapper.writeValueAsString(ec.toErrorResponseEntity().getBody());
        response.getWriter().write(jsonBody);
    }
}

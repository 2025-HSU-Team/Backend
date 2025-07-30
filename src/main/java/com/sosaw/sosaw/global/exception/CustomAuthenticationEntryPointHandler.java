package com.sosaw.sosaw.global.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sosaw.sosaw.domain.user.exception.UserErrorCode;
import com.sosaw.sosaw.global.response.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        BaseResponse errorResponse = BaseResponse.of(UserErrorCode.NEED_LOGIN_401);
        String body = new ObjectMapper().writeValueAsString(errorResponse);

        response.getWriter().write(body);
    }
}

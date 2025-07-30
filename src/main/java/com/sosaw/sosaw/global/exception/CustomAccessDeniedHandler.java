package com.sosaw.sosaw.global.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sosaw.sosaw.domain.user.exception.UserErrorCode;
import com.sosaw.sosaw.global.response.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

// 사용자가 로그인은 했지만, 접근 권한이 없는 URL에 요청했을 때
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, IOException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);  // 인증은 되었지만 권한 없음
        response.setContentType("application/json;charset=UTF-8");

        BaseResponse errorResponse = BaseResponse.of(UserErrorCode.CAN_NOT_ACCESS_403);
        String body = new ObjectMapper().writeValueAsString(errorResponse);

        response.getWriter().write(body);
    }
}

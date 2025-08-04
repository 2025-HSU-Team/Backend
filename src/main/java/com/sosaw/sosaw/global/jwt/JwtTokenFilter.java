package com.sosaw.sosaw.global.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// 매 HTTP 요청마다 실행되는 JWT 인증 필터
// Authorization 헤더에서 JWT를 꺼내고 -> 유효한지 증하고 -> 검증되면 사용자 인증정보를 SecurityConxt에 주입
@RequiredArgsConstructor
@Component
// OncePerRequestFilter: 매 요청마다 한 번 실행되는 JWT 필터
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    // 요청이 들어올 때마다 JWT 토큰을 검증함
    // 유효한 경우 인증 정보를 SecurityContext에 저장
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Authorization 헤더에서 "Bearer {token}" 형태의 JWT를 꺼냄
        String token = extractToken(request);

        // 2. validateToken: 토큰 유효성 검사
        if (token != null && jwtTokenProvider.validateToken(token)) { // 토큰이 null이 아니고, 서명 유효성, 만료기간 등을 통과했는지 검증함
            // 3. 토큰으로 사용자 인증 객체 생성
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            // 현재 요청의 SecurityContext에 인증 정보 넣기
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }

    // Authorization 헤더에서 "Bearer {token}" 형식의 JWT를 추출
    private String extractToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization"); // Authorization 헤더 가져오기
        if (bearer != null && bearer.startsWith("Bearer ")) { // 헤더가 null이 아니고, "Bearer "로 시작하는지 확인함
            System.out.println("Token: " + bearer);
            return bearer.substring(7); // "Bearer " 이후의 실제 토큰 값만 사용
        }
        return null; // 형식이 올바르지 않으면 -> null 반환
    }
}

package com.sosaw.sosaw.global.jwt;

import com.sosaw.sosaw.domain.user.entity.User;
import com.sosaw.sosaw.domain.user.repository.UserRepository;
import com.sosaw.sosaw.global.security.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

// JWT 토큰을 만들고, 검증하고, 토큰에서 사용자 정보 꺼내는 역할
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final UserRepository userRepository;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiration; // 밀리초 단위

    private Key key;

    // secretKey 비밀 문자열을 가지고 옴 -> 문자열을 바이트 배열로 바꿈 -> 이 바이트 배열을 바탕으로 서명에 쓸 Key 객체 생성
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // 로그인/회원가입 성공 시 JWT 발급
    public String createToken(User user) {
        Date now = new Date(); // 현재 시간
        Date expiry = new Date(now.getTime() + expiration); // 만료 시간

        return Jwts.builder()
                // subject: JWT에 담긴 정보의 주체. 일반적으로 사용자의 고유 식별자나 아이디 사용
                .subject(String.valueOf(user.getUserId()))
                // claim: JWT 안에 넣고 싶은 추가 정보들
                .claim("email", user.getLoginId())
                .claim("role", user.getRole())
                // issuedAt: 토큰이 발급된 시간 -> 보안상 이유로 보통 넣음
                .issuedAt(now)
                // expiration: 만료 시간
                .expiration(expiry)
                // signWith: 서명(Signature)을 위한 키 설정 (자동으로 알고리즘 선택됨 (현재: HS256))
                .signWith(key)
                // compact: 설정한 모든 정보들을 하나의 문자열(String) 토큰으로 압축해서 반환
                .compact();
    }

    // 토큰 유효성 + 만료 검증
    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // 토큰 파싱해서 Claims 추출
    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key)
                .build() // parser 생성
                .parseSignedClaims(token) // 서명 검증 + 만료 검증 + 파싱!!
                .getPayload(); // payload 부분(Claims)만 반환
    }

    // JWT 토큰에서 사용자 정보를 꺼내서, Spring Security가 이해할 수 있는 인증 객체 (Authentication)로 바꿔주는 역할
    public Authentication getAuthentication(String token) {

        Claims claims = getClaims(token); // 토큰을 파싱해서 Payload 정보를 꺼냄

        Long userId = Long.parseLong(claims.getSubject()); // subject는 userId

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 회원 정보 없음 (userId=" + userId + ")"));

        // 조회한 User 객체를 CustomUserDetails로 감쌈 → Spring Security는 UserDetails를 통해 로그인 유저 정보를 관리함
        CustomUserDetails userDetails = new CustomUserDetails(user);

        // Spring Security에서 인증된 유저임을 나타내는 객체를 만들어주는 부분
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }
}
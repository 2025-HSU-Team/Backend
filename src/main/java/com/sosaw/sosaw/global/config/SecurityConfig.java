package com.sosaw.sosaw.global.config;

import com.sosaw.sosaw.domain.user.service.CustomOAuth2UserService;
import com.sosaw.sosaw.global.exception.CustomAccessDeniedHandler;
import com.sosaw.sosaw.global.exception.CustomAuthenticationEntryPointHandler;
import com.sosaw.sosaw.global.jwt.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPointHandler customAuthenticationEntryPointHandler;
    private final JwtTokenFilter jwtTokenFilter;
    private final CustomOAuth2UserService customOAuth2UserService;

    // 비밀번호를 DB에 암호화해서 저장할 때 사용하는 인코더
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Spring Security 내부에서 인증 로직을 실행할 때 사용하는 핵심 객체
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // 필터 체인 설정
    // HttpSecurity를 통해 시큐리티 정책을 정의함
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // JWT 기반 API 서버에서는 세션/쿠키를 안 쓰므로 보통 비활성화
                .csrf(csrf -> csrf.disable())

                // 인가 정책 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/signup", "/api/users/signin").permitAll() //누구나 접근 가능
                        .requestMatchers("/api/users/profile").hasRole("ADMIN") // ADMIN 역할 가진 사용자만 접근 가능
                        .requestMatchers("kakao-login-test.html").permitAll()
                        .requestMatchers("/oauth2/**", "/login/oauth2/**").permitAll()
                        .requestMatchers("/static/js/**","/static/css/**","/static/img/**"
                                ,"/swagger-ui/**","/v3/api-docs/**").permitAll() // swagger
                        .anyRequest().authenticated() // 로그인한 사용자만 접근 허용
                )

                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService) // 직접 만든 service
                        )
                        // 성공
                        .successHandler((request, response, authentication) -> {
                            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
                            String jwt = (String) oAuth2User.getAttributes().get("jwt");
                            response.sendRedirect("http://localhost:3000/oauth-success?token=" + jwt);
                        })
                        // 실패
                        .failureHandler((request, response, exception) -> {
                            System.out.println("소셜 로그인 실패 발생");
                            exception.printStackTrace(); // 어떤 오류인지 출력
                            response.sendRedirect("http://localhost:3000/oauth-fail");
                        })
                )

                // Spring Security 기본 로그인 필터보다 앞에 우리가 만든 JwtTokenFilter를 실행함
                // 이 필터는 요청 헤더에서 JWT 토큰을 추출하고, 검증하고, SecurityContext에 인증정보를 주입하는 역할
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)

                // 인가 실패(예: 일반 유저가 /api/user/profile 접근 시) 상황에서 커스텀 에러 응답을 반환하도록 함
                // CustomAccessDeniedHandler에서 에러 메시지, HTTP 상태코드 등을 정의할 수 있음
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(customAccessDeniedHandler)
                        .authenticationEntryPoint(customAuthenticationEntryPointHandler)
                );
        return http.build();
    }
}
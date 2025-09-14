package com.sosaw.sosaw.global.config;

import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI openAPI() {
        // API 기본 정보
        Info info = new Info()
                .title("sosaw")
                .version("v0.0.1")
                .description("공학경진대회 sosaw의 API 명세서");

        // Bearer(JWT) 스키마 정의
        SecurityScheme bearerAuthScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)     // HTTP 인증
                .scheme("bearer")                   // 스키마는 bearer
                .bearerFormat("JWT")                // 표시용 포맷
                .in(SecurityScheme.In.HEADER)       // 헤더에 담음
                .name("Authorization");             // 헤더 이름

        // 전역 보안 요구사항, 전체 엔드포인트에 적용
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(SECURITY_SCHEME_NAME);

        return new OpenAPI()
                .info(info)
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME, bearerAuthScheme))
                .addSecurityItem(securityRequirement);
    }
}
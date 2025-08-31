package com.sosaw.sosaw.global.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("sosaw")
                .version("v0.0.1")
                .description("공학경진대회 sosaw의 API 명세서");
        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}

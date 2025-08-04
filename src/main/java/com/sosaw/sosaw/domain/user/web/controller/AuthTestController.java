package com.sosaw.sosaw.domain.user.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthTestController {
    @GetMapping("/api/test/auth")
    public String testAuth() {
        return "인증된 사용자입니다.";
    }
}

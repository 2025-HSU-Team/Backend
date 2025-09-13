package com.sosaw.sosaw.domain.user.web.controller;

import com.sosaw.sosaw.domain.user.service.UserService;
import com.sosaw.sosaw.domain.user.web.dto.SignUpReq;
import com.sosaw.sosaw.global.response.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignUpReq signUpReq) {
        userService.signUp(signUpReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.created(null));
    }
}

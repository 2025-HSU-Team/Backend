package com.sosaw.sosaw.domain.user.web.controller;

import com.sosaw.sosaw.domain.user.service.UserService;
import com.sosaw.sosaw.domain.user.web.dto.SignInReq;
import com.sosaw.sosaw.domain.user.web.dto.SignInRes;
import com.sosaw.sosaw.domain.user.web.dto.SignUpReq;
import com.sosaw.sosaw.global.response.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignUpReq signUpReq) {
        userService.signUp(signUpReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.created(null));
    }

    // 아이디 중복 확인
    @GetMapping("/signup/checkId")
    public ResponseEntity<SuccessResponse<?>> checkIdDuplicate(@RequestParam("id") String id) {
        boolean isDuplicate = userService.isLoginIdDuplicate(id);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.ok(isDuplicate));
    }

    // 로그인
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody @Valid SignInReq signInReq) {
        SignInRes signInRes = userService.signIn(signInReq);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.ok(signInRes));
    }
}

package com.sosaw.sosaw.domain.user.service;

import com.sosaw.sosaw.domain.user.web.dto.SignInReq;
import com.sosaw.sosaw.domain.user.web.dto.SignInRes;
import com.sosaw.sosaw.domain.user.web.dto.SignUpReq;

public interface UserService {

    // 회원가입
    void signUp(SignUpReq signUpReq);

    // 회원가입시, 아이디 중복 확인
    boolean isLoginIdDuplicate(String loginId);

    // 로그인
    SignInRes signIn(SignInReq signInReq);
}

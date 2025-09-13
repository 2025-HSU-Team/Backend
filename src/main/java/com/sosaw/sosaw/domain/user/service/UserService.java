package com.sosaw.sosaw.domain.user.service;

import com.sosaw.sosaw.domain.user.web.dto.SignUpReq;

public interface UserService {

    // 회원가입
    void signUp(SignUpReq signUpReq);
}

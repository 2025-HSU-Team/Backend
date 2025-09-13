package com.sosaw.sosaw.domain.user.exception;

import com.sosaw.sosaw.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.sosaw.sosaw.global.constant.StaticValue.*;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements BaseResponseCode {
    CAN_NOT_ACCESS_403("CAN_NOT_ACCESS_403", FORBIDDEN, "접근 권한이 없습니다."),
    NEED_LOGIN_401("NEED_LOGIN_401", UNAUTHORIZED, "인증이 필요합니다."),
    USER_ALREADY_EXIST_409("USER_ALREADY_EXIST_409", CONFLICT, "이미 존재하는 사용자입니다."),
    USER_NOT_FOUND_404("USER_NOT_FOUND_404", NOT_FOUND, "존재하지 않는 사용자입니다."),
    PASSWORD_MISMATCH_401("PASSWORD_MISMATCH_401", UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");

    private final String code;
    private final int httpStatus;
    private final String message;
}

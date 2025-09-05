package com.sosaw.sosaw.domain.user.exception;

import com.sosaw.sosaw.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.sosaw.sosaw.global.constant.StaticValue.FORBIDDEN;
import static com.sosaw.sosaw.global.constant.StaticValue.UNAUTHORIZED;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements BaseResponseCode {
    CAN_NOT_ACCESS_403("CAN_NOT_ACCESS_403", FORBIDDEN, "접근 권한이 없습니다."),
    NEED_LOGIN_401("NEED_LOGIN_401", UNAUTHORIZED, "인증이 필요합니다.");

    private final String code;
    private final int httpStatus;
    private final String message;
}

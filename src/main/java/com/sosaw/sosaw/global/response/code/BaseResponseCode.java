package com.sosaw.sosaw.global.response.code;

// 공통 응답 코드 인터페이스
public interface BaseResponseCode {
    String getCode();
    int getHttpStatus();
    String getMessage();
}

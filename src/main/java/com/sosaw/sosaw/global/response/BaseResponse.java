package com.sosaw.sosaw.global.response;

import com.sosaw.sosaw.global.response.code.BaseResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@RequiredArgsConstructor
@ToString
public class BaseResponse {
    private final Boolean isSuccess;
    private final String code;
    private final String message;
    private final String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    // 성공 여부와 baseResponseCode를 기반으로 넘김
    public static BaseResponse of(Boolean isSuccess, BaseResponseCode baseResponseCode) {
        return new BaseResponse(isSuccess, baseResponseCode.getCode(), baseResponseCode.getMessage());
    }

    // 성공 여부와 baseResponseCode, 그리고 message를 넘겨주는 방식
    public static BaseResponse of(Boolean isSuccess, BaseResponseCode baseResponseCode, String message) {
        return new BaseResponse(isSuccess, baseResponseCode.getCode(), message);
    }

    // 성공 여부와 코드, 그리고 message를 넘겨주는 방식
    public static BaseResponse of(Boolean isSuccess, String code, String message) {
        return new BaseResponse(isSuccess, code, message);
    }
}
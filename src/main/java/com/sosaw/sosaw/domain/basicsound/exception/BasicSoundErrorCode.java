package com.sosaw.sosaw.domain.basicsound.exception;

import com.sosaw.sosaw.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.sosaw.sosaw.global.constant.StaticValue.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum BasicSoundErrorCode implements BaseResponseCode {
    NOT_FOUND_BASIC_SOUND_404("BASIC_SOUND_404_1", NOT_FOUND, "해당 기본 소리를 찾을 수 없습니다.");

    private final String code;
    private final int httpStatus;
    private final String message;
}

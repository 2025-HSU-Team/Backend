package com.sosaw.sosaw.domain.customsound.exception;

import com.sosaw.sosaw.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.sosaw.sosaw.global.constant.StaticValue.*;

@Getter
@AllArgsConstructor
public enum CustomSoundErrorCode implements BaseResponseCode {
    UNSUPPORTED_EXTENSION_415("CUSTOM_SOUND_415", UNSUPPORTED_MEDIA_TYPE, "지원하지 않는 파일 형식입니다. (.wav만 허용)"),
    FILE_PROCESS_FAILED_500("CUSTOM_SOUND_500_1", INTERNAL_SERVER_ERROR, "파일 처리 중 오류가 발생했습니다."),
    FASTAPI_CALL_FAILED_500("CUSTOM_SOUND_500_2", INTERNAL_SERVER_ERROR, "FastAPI MFCC 호출 실패"),
    NOT_FOUND_SOUND_404("CUSTOM_SOUND_404_1", NOT_FOUND, "해당 소리를 찾을 수 없습니다.");

    private final String code;
    private final int httpStatus;
    private final String message;
}

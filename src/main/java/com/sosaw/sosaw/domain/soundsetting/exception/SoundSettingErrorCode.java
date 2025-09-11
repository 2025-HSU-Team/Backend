package com.sosaw.sosaw.domain.soundsetting.exception;

import com.sosaw.sosaw.global.response.code.BaseResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.sosaw.sosaw.global.constant.StaticValue.BAD_REQUEST;
import static com.sosaw.sosaw.global.constant.StaticValue.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum SoundSettingErrorCode implements BaseResponseCode {
    UNSUPPORTED_SOUND_TYPE_400("SOUND_SETTING_400_1", BAD_REQUEST, "지원하지 않는 사운드 타입입니다."),
    NOT_FOUND_CUSTOM_SOUND_404("SOUND_SETTING_404_2", NOT_FOUND, "해당 커스텀 사운드를 찾을 수 없습니다.");

    private final String code;
    private final int httpStatus;
    private final String message;
}

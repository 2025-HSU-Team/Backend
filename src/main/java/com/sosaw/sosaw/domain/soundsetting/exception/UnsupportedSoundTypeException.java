package com.sosaw.sosaw.domain.soundsetting.exception;

import com.sosaw.sosaw.global.exception.BaseException;

public class UnsupportedSoundTypeException extends BaseException {
    public UnsupportedSoundTypeException() {
        super(SoundSettingErrorCode.UNSUPPORTED_SOUND_TYPE_400);
    }
}
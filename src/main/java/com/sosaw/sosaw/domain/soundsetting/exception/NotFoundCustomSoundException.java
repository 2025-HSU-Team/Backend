package com.sosaw.sosaw.domain.soundsetting.exception;

import com.sosaw.sosaw.global.exception.BaseException;

public class NotFoundCustomSoundException extends BaseException {
    public NotFoundCustomSoundException() {
        super(SoundSettingErrorCode.NOT_FOUND_CUSTOM_SOUND_404);
    }
}

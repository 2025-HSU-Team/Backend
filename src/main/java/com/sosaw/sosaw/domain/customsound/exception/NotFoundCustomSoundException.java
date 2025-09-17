package com.sosaw.sosaw.domain.customsound.exception;

import com.sosaw.sosaw.global.exception.BaseException;

public class NotFoundCustomSoundException extends BaseException {
    public NotFoundCustomSoundException() {
        super(CustomSoundErrorCode.NOT_FOUND_CUSTOM_SOUND_404);
    }
}

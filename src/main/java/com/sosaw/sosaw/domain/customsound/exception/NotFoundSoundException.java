package com.sosaw.sosaw.domain.customsound.exception;

import com.sosaw.sosaw.global.exception.BaseException;

public class NotFoundSoundException extends BaseException {
    public NotFoundSoundException() {
        super(CustomSoundErrorCode.NOT_FOUND_SOUND_404);
    }
}

package com.sosaw.sosaw.domain.basicsound.exception;

import com.sosaw.sosaw.global.exception.BaseException;

public class NotFoundBasicSoundException extends BaseException {
    public NotFoundBasicSoundException() {
        super(BasicSoundErrorCode.NOT_FOUND_BASIC_SOUND_404);
    }
}

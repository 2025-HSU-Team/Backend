package com.sosaw.sosaw.domain.customsound.exception;

import com.sosaw.sosaw.global.exception.BaseException;

public class FastapiCallFailedException extends BaseException {
    public FastapiCallFailedException() {
        super(CustomSoundErrorCode.FASTAPI_CALL_FAILED_500);
    }
}

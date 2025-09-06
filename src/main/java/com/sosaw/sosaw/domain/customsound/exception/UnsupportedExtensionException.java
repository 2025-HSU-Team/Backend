package com.sosaw.sosaw.domain.customsound.exception;

import com.sosaw.sosaw.global.exception.BaseException;
import com.sosaw.sosaw.global.response.code.BaseResponseCode;

public class UnsupportedExtensionException extends BaseException {
    public UnsupportedExtensionException() {
        super(CustomSoundErrorCode.UNSUPPORTED_EXTENSION_415);
    }
}

package com.sosaw.sosaw.domain.customsound.exception;

import com.sosaw.sosaw.global.exception.BaseException;

public class FileProcessFailedException extends BaseException {
    public FileProcessFailedException() {
        super(CustomSoundErrorCode.FILE_PROCESS_FAILED_500);
    }
}

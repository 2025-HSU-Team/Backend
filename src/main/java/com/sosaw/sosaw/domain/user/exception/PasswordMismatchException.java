package com.sosaw.sosaw.domain.user.exception;

import com.sosaw.sosaw.global.exception.BaseException;

public class PasswordMismatchException extends BaseException {
    public PasswordMismatchException() {super(UserErrorCode.PASSWORD_MISMATCH_401);}
}

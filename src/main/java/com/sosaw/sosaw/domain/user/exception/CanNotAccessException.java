package com.sosaw.sosaw.domain.user.exception;

import com.sosaw.sosaw.global.exception.BaseException;

public class CanNotAccessException extends BaseException {
    public CanNotAccessException() { super(UserErrorCode.CAN_NOT_ACCESS_403); }
}

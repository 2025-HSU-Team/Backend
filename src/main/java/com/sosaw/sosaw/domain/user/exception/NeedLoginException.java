package com.sosaw.sosaw.domain.user.exception;

import com.sosaw.sosaw.global.exception.BaseException;

public class NeedLoginException extends BaseException {
    public NeedLoginException() {super(UserErrorCode.NEED_LOGIN_401);}
}

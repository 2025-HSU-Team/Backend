package com.sosaw.sosaw.domain.user.exception;

import com.sosaw.sosaw.global.exception.BaseException;

public class UserAlreadyExistException extends BaseException {
    public UserAlreadyExistException() {
        super(UserErrorCode.USER_ALREADY_EXIST_409);
    }
}

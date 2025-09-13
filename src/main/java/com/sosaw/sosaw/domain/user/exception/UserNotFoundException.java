package com.sosaw.sosaw.domain.user.exception;

import com.sosaw.sosaw.global.exception.BaseException;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException() {super(UserErrorCode.USER_NOT_FOUND_404);}
}

package com.futurenet.sorisoopbackend.auth.application.exception;

import com.futurenet.sorisoopbackend.global.exception.ErrorCode;
import com.futurenet.sorisoopbackend.global.exception.RestApiException;

public class AuthException extends RestApiException {
    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}

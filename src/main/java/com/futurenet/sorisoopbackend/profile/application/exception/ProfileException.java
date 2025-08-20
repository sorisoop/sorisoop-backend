package com.futurenet.sorisoopbackend.profile.application.exception;

import com.futurenet.sorisoopbackend.global.exception.ErrorCode;
import com.futurenet.sorisoopbackend.global.exception.RestApiException;

public class ProfileException extends RestApiException {
    public ProfileException(ErrorCode errorCode) {
        super(errorCode);
    }
}

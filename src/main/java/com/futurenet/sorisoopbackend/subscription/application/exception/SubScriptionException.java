package com.futurenet.sorisoopbackend.subscription.application.exception;

import com.futurenet.sorisoopbackend.global.exception.ErrorCode;
import com.futurenet.sorisoopbackend.global.exception.RestApiException;

public class SubScriptionException extends RestApiException {

    public SubScriptionException(ErrorCode errorCode) {
        super(errorCode);
    }
}


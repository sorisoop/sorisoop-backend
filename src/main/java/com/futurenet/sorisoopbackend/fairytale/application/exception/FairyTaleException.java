package com.futurenet.sorisoopbackend.fairytale.application.exception;

import com.futurenet.sorisoopbackend.global.exception.ErrorCode;
import com.futurenet.sorisoopbackend.global.exception.RestApiException;

public class FairyTaleException extends RestApiException {

    public FairyTaleException(ErrorCode errorCode) {
        super(errorCode);
    }
}

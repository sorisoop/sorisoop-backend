package com.futurenet.sorisoopbackend.customfairytale.application.exception;

import com.futurenet.sorisoopbackend.global.exception.ErrorCode;
import com.futurenet.sorisoopbackend.global.exception.RestApiException;

public class CustomFairyTaleException extends RestApiException {

    public CustomFairyTaleException(ErrorCode errorCode) {
        super(errorCode);
    }
}

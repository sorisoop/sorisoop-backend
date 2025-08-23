package com.futurenet.sorisoopbackend.brandPayToken.application.exception;

import com.futurenet.sorisoopbackend.global.exception.ErrorCode;
import com.futurenet.sorisoopbackend.global.exception.RestApiException;

public class BrandPayTokenException extends RestApiException {
    public BrandPayTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}

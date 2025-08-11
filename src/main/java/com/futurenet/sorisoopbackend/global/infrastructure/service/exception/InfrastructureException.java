package com.futurenet.sorisoopbackend.global.infrastructure.service.exception;

import com.futurenet.sorisoopbackend.global.exception.ErrorCode;
import com.futurenet.sorisoopbackend.global.exception.RestApiException;

public class InfrastructureException extends RestApiException {
    public InfrastructureException(ErrorCode errorCode) {
        super(errorCode);
    }
}

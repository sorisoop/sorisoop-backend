package com.futurenet.sorisoopbackend.fairytale.application.exception;

import com.futurenet.sorisoopbackend.global.exception.ErrorCode;
import com.futurenet.sorisoopbackend.global.exception.RestApiException;

public class FavoriteException extends RestApiException {

    public FavoriteException(ErrorCode errorCode) {
        super(errorCode);
    }
}

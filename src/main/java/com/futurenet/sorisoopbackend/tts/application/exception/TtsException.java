package com.futurenet.sorisoopbackend.tts.application.exception;

import com.futurenet.sorisoopbackend.global.exception.ErrorCode;
import com.futurenet.sorisoopbackend.global.exception.RestApiException;

public class TtsException extends RestApiException {
    public TtsException(ErrorCode errorCode) {
        super(errorCode);
    }
}

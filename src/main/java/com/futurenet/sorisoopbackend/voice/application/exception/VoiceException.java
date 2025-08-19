package com.futurenet.sorisoopbackend.voice.application.exception;

import com.futurenet.sorisoopbackend.global.exception.ErrorCode;
import com.futurenet.sorisoopbackend.global.exception.RestApiException;

public class VoiceException extends RestApiException {
    public VoiceException(ErrorCode errorCode ) {
        super(errorCode);
    }
}

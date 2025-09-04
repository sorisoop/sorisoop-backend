package com.futurenet.sorisoopbackend.mission.application.exception;

import com.futurenet.sorisoopbackend.global.exception.ErrorCode;
import com.futurenet.sorisoopbackend.global.exception.RestApiException;

public class MissionException extends RestApiException {

    public MissionException(ErrorCode errorCode) {
        super(errorCode);
    }
}

package com.futurenet.sorisoopbackend.global.exception;

import com.futurenet.sorisoopbackend.global.exception.util.ExceptionMessageUtil;
import com.futurenet.sorisoopbackend.global.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ApiResponse<>("ER000", e.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<?> handleRestApiException(RestApiException e) {
        ErrorCode ec = e.getErrorCode();
        log.error(ec.getMessage(), e);
        return new ResponseEntity<>(new ApiResponse<>(ec.getCode(), ec.getMessage(), null), ec.getHttpStatus());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, HandlerMethodValidationException.class})
    public ResponseEntity<?> handleValidationExceptions(Exception e) {
        String message = ExceptionMessageUtil.makeValidationExceptionMessage(e);
        return new ResponseEntity<>(new ApiResponse<>("VE000", message, null), HttpStatus.BAD_REQUEST);
    }

}

package com.rookies5.myspringbootlab.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final ErrorObject errorObject;

    public BusinessException(HttpStatus httpStatus, String code, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorObject = ErrorObject.builder()
                .code(code)
                .message(message)
                .build();
    }
}

package com.rookies5.myspringbootlab.common.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;
    private final ErrorObject errorObject;

    public BusinessException(ErrorCode errorCode, Object... args) {
        super(errorCode.formatMessage(args));
        this.errorCode = errorCode;
        this.errorObject = ErrorObject.builder()
                .code(errorCode.getCode())
                .message(errorCode.formatMessage(args))
                .build();
    }
}

package com.rookies5.myspringbootlab.common.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorObject {
    private String code;
    private String message;
}

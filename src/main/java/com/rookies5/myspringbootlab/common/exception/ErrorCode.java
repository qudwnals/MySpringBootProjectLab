package com.rookies5.myspringbootlab.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    BOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "BOOK_NOT_FOUND", "도서를 찾을 수 없습니다. value=%s"),
    ISBN_DUPLICATE(HttpStatus.CONFLICT, "ISBN_DUPLICATE", "이미 사용 중인 ISBN입니다. isbn=%s");

    private final HttpStatus httpStatus;
    private final String code;
    private final String messageTemplate;

    public String formatMessage(Object... args) {
        return String.format(messageTemplate, args);
    }
}

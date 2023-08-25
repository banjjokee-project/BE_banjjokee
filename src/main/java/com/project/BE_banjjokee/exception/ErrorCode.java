package com.project.BE_banjjokee.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // User
    U001(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),

    // Post
    P001(HttpStatus.NOT_FOUND, "존재하지 않는 컨텐츠입니다."),

    // Comment
    C001(HttpStatus.NOT_FOUND,"존재하지 않는 컨텐츠입니다."),
    C002(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    C003(HttpStatus.BAD_REQUEST, "잘못된 입력입니다."),
    C004(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.");

    private final HttpStatus errorCode;

    private final String errorMessage;

    ErrorCode(HttpStatus errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}

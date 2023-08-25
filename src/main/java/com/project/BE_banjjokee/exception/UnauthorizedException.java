package com.project.BE_banjjokee.exception;

public class UnauthorizedException extends BusinessException{

    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }

}

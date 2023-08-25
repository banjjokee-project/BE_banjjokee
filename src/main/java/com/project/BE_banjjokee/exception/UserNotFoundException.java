package com.project.BE_banjjokee.exception;

public class UserNotFoundException extends BusinessException{

    public UserNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

}

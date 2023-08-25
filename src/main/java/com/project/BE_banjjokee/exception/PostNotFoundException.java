package com.project.BE_banjjokee.exception;

public class PostNotFoundException extends BusinessException{

    public PostNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

}

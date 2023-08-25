package com.project.BE_banjjokee.exception;

public class CommentNotAuthorizedException extends BusinessException{

    public CommentNotAuthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }

}

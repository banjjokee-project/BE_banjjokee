package com.project.BE_banjjokee.exception;

public class CommentInvalidRequestException extends BusinessException{

    public CommentInvalidRequestException(ErrorCode errorCode) {
        super(errorCode);
    }

}
